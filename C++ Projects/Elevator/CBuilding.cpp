     //////////////////////////////////////////////////
    //  Program: Elevator Sim : CBuilding.cpp 
   //    Author: Victor Trucker
  //       Date: 06/22/2015
 // Description: Code for the 'Building' class
//////////////////////////////////////////////////
#include "CBuilding.h"

CBuilding::CBuilding( int numFloors )
{
    srand( (unsigned)time(NULL) );

    theElevator   = new CElevator;
    m_hConsole    = GetStdHandle( STD_OUTPUT_HANDLE );
    m_nFinished   = 0;
    m_nPosition.X = 0;
    m_nPosition.Y = 0;
    m_nNumFloors  = numFloors;
    initBuilding();
}


CBuilding::~CBuilding()
{
    delete theElevator;
}


void CBuilding::initBuilding()
{
    createBuilding();
    addElevator();
    spawnPeople();
    m_nFinished = 0;
}


void CBuilding::sleep( unsigned int mseconds ) 
{ 
    clock_t goal = mseconds + clock();
    while ( goal > clock() );
}


bool CBuilding::createBuilding()
{
    int
        nLup = 0;

    cout << endl << endl;

    // 0, 1, 2, 3 // roof and ceiling
    for ( nLup = 0; nLup < 4; nLup++ )
    {
        cout << BUILDING[building1[nLup]];
    }

    // 4, 5 once for each floor
    for ( nLup = 1; nLup < m_nNumFloors; nLup++ )
    {
        cout << BUILDING[4];
        cout << BUILDING[5];
    }

    // 4, 6, 7, 8 // bottom floor and base
    for ( nLup = 4; nLup < 8; nLup++ )
    {
        cout << BUILDING[building1[nLup]];
    }

    return true;
}


void CBuilding::addElevator()
{
    int
        nOffset = 0,
        nIndex  = ((((TOP_FLOOR + 1) - BOT_FLOOR) + 2) * 2) - 1;

    m_nPosition.X = ELEV_BOX;

    for ( m_nPosition.Y = nIndex; m_nPosition.Y < nIndex + 3; m_nPosition.Y++, nOffset++ )
    {
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( ELEVATOR[EL_TOP + nOffset].c_str() );
    }

    sleep( 333 );
}


bool CBuilding::spawnPeople()
{
    int
        nLup = 0;

    for ( nLup = 0; nLup < MAX_PEOPLE; nLup++ )
    {
        char
            chName = (char)(nLup + 65);
        CPerson
            *person = new CPerson;

        person->setName( chName );
        person->setCurrFloor( BOT_FLOOR );
        person->setIndex_X( ENTRY );
        person->setTopFloor( m_nNumFloors );
        person->setIndex_Y( (((TOP_FLOOR + 1) - BOT_FLOOR) + 2) * 2 );
        person->setActive( false );

        m_vecPeople.push_back( *person );

        delete person;
    }

  return true;
}


void CBuilding::openForBusiness()
{
    int
        nCurrFloor = theElevator->getCurrFloor();

    do
    {
        checkForRiders();
        nCurrFloor = moveElevatorToRoof( nCurrFloor );
        theElevator->setCurrFloor( nCurrFloor );
        openDoor( CLOSE );
        nCurrFloor = moveElevatorToBase( nCurrFloor );
        theElevator->setCurrFloor( nCurrFloor );
    }
    while ( m_nFinished < MAX_PEOPLE );

    sleep( 500 );
}


void CBuilding::openDoor( bool bOpen ) // true=open false=close
{
    int
        nIndex = (((TOP_FLOOR + 1) - theElevator->getCurrFloor() ) + 2) * 2;

    if ( bOpen )
    {
        sleep( 192 );
        m_nPosition.X = ELEV_BOX - 1;
        m_nPosition.Y = nIndex;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( PIECES[SPACE].c_str() );
        sleep( 192 );

        theElevator->openDoors( bOpen );
    }
    else
    {
        theElevator->openDoors( bOpen );

        m_nPosition.X = ELEV_BOX - 1;
        m_nPosition.Y = nIndex;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( PIECES[PIPE].c_str() );
        sleep( 192 );
    }
}


bool CBuilding::verifyRider( void )
{
    _vecPIt
        vecPItL;
    bool
        bRet = false;

    m_nMaxFloor = 0;

    for ( vecPItL = m_vecPeople.begin(); vecPItL != m_vecPeople.end(); vecPItL++ )
    {
        if ( vecPItL->getWantsElevator() )
        {
            m_nMaxFloor = ( vecPItL->getCurrFloor() > m_nMaxFloor )
                          ? vecPItL->getCurrFloor() : m_nMaxFloor;
        }

        m_nMaxFloor = ( vecPItL->getDesiredFloor() > m_nMaxFloor )
                        ? vecPItL->getDesiredFloor() : m_nMaxFloor;

        if ( vecPItL->getRider() ) bRet = true;
    }

    return bRet;
}


void CBuilding::checkForRiders()
{
    bool
        bRider     = false,
        bHit       = false;
    char
        chName     = ' ';
    int
        nLup       = 0,
        nIndex     = (((TOP_FLOOR + 1) - BOT_FLOOR) + 2) * 2,
        nCurrFloor = theElevator->getCurrFloor();

    do
    {
        bHit      = false;

        for ( m_vecPIt = m_vecPeople.begin(), nLup = 0; m_vecPIt != m_vecPeople.end(); m_vecPIt++, nLup++ )
        {
            chName = (char)(nLup + 65);

            if ( !m_vecPIt->getFinished() )
            {
                bool
                    bWants = (((rand() % 6) + 1) == 3);

                if ( m_vecPIt->setWantsElevator( bWants ) )
                {
                    m_vecPIt->setDesiredFloor( m_vecPIt->getCurrFloor() );
                    m_vecPIt->setActive( true );
                    m_vecPIt->setName( chName );
                    m_vecPIt->setIndex_X( WAITER );

                    if ( m_vecPIt->getCurrFloor() == BOT_FLOOR && !m_vecPIt->getRider() )
                    {
                        openDoor( OPEN );
                        m_bDoorIsOpen = true;
                        m_vecPIt->setIndex_X( RIDER );
                        m_vecPIt->setIndex_Y( nIndex );
                        m_vecPIt->setRider( true );
                        bRider = verifyRider();
                        m_vecPIt->enterBuilding( true );
                        m_vecPIt->enterElevator( true, bRider );
                        m_vecPIt->setWantsElevator( false );
                    }
                    else
                    {
                        m_vecPIt->callElevator();
                    }

                    bHit = true;
                }
                else
                {
                    m_vecPIt->setActive( false );
                }
            }
        }
    }
    while ( !bHit );

    if (m_bDoorIsOpen) openDoor( CLOSE );
    m_bDoorIsOpen = false;
}


int CBuilding::moveElevatorToRoof( int nCurrFloor )
{
    _vecPIt
        vecPItL;
    int
        nIndex = 0;
    char
        chName = ' ';


    openDoor( CLOSE );

    do
    {
        nCurrFloor = moveElevatorOneFloor( nCurrFloor, UP );
        nIndex = (((TOP_FLOOR + 1) - nCurrFloor) + 2) * 2;

        for ( m_vecPIt = m_vecPeople.begin(); m_vecPIt != m_vecPeople.end(); m_vecPIt++ )
        {
            chName = m_vecPIt->getName();

        if ( m_vecPIt->getRider() ) 
        {
            m_vecPIt->setCurrFloor( nCurrFloor );
        }

        if ( m_vecPIt->getCurrFloor() == nCurrFloor )
        {
            if ( m_vecPIt->getRider() && nCurrFloor == m_vecPIt->getDesiredFloor() )
            {
                openDoor( OPEN );
                m_bDoorIsOpen = true;
                m_nPosition.X = RIDER;
                m_nPosition.Y = nIndex;
                SetConsoleCursorPosition( m_hConsole, m_nPosition );
                printf( ( verifyRider() ) ? PIECES[PERSON].c_str() : PIECES[SPACE].c_str() );

                m_vecPIt->setRider( false );
                m_vecPIt->clearDesiredFloor();
                m_vecPIt->setWantsElevator( false );
                m_vecPIt->setCurrFloor( nCurrFloor );
                m_vecPIt->enterElevator( false, verifyRider() );
            }
            else if ( m_vecPIt->getWantsElevator() && !m_vecPIt->getRider() )
            {
                if ( m_vecPIt->getIndex_X() > 0 )
                {
                    m_vecPIt->callElevator();
                }

                openDoor( OPEN );
                m_bDoorIsOpen = true;
                m_vecPIt->setRider( true );
                m_vecPIt->setIndex_X( RIDER );
                m_vecPIt->setIndex_Y( nIndex );
                m_vecPIt->enterElevator( true, verifyRider() );
                m_vecPIt->setWantsElevator( false );
            }
        }
    }

    if (m_bDoorIsOpen) openDoor( CLOSE );
    m_bDoorIsOpen = false;

    }
    while ( nCurrFloor < m_nMaxFloor );

    return nCurrFloor;
}


int CBuilding::moveElevatorToBase( int nCurrFloor )
{
    _vecPIt
        vecPItL;
    int
        nIndex = 0;

    openDoor( CLOSE );

    do
    {
        nCurrFloor = moveElevatorOneFloor( nCurrFloor, DN );
        theElevator->setCurrFloor( nCurrFloor );
        nIndex = (((TOP_FLOOR + 1) - nCurrFloor) + 2) * 2;

        for ( vecPItL = m_vecPeople.begin(); vecPItL != m_vecPeople.end(); vecPItL++ )
        {
            if ( vecPItL->getRider() && vecPItL->getDesiredFloor() == nCurrFloor )
            {
                vecPItL->setCurrFloor( nCurrFloor );
                openDoor( OPEN );
                m_bDoorIsOpen = true;
                vecPItL->setRider( false );
                vecPItL->clearDesiredFloor();
                vecPItL->setWantsElevator( false );
                vecPItL->enterElevator( false, verifyRider() );

                m_nPosition.X = RIDER;
                m_nPosition.Y = nIndex;
                SetConsoleCursorPosition( m_hConsole, m_nPosition );
                printf( ( verifyRider() ) ? PIECES[PERSON].c_str() : PIECES[SPACE].c_str() );

                vecPItL->setCurrFloor( nCurrFloor );

                if ( nCurrFloor == BOT_FLOOR && vecPItL->getIndex_X() >= WAITER )
                {
                  vecPItL->setActive( false );
                  vecPItL->setFinished( true );
                  vecPItL->enterBuilding( false );
                  m_nFinished++;
                }
            }
        }

        if (m_bDoorIsOpen) openDoor( CLOSE );
        m_bDoorIsOpen = false;

        if ( BOT_FLOOR == nCurrFloor ) break;
    }
    while ( 1 );

    return BOT_FLOOR;
}


int CBuilding::moveElevatorOneFloor( int nCurrFloor, int nDirection )
{
    int
        nOffset = 0,
        nIndex  = (((TOP_FLOOR + 1) - nCurrFloor) + 2) * 2;
    _vecPIt
        vecPItL;
    bool
        bRider = verifyRider();

    theElevator->setRider( bRider );

    if ((BOT_FLOOR == nCurrFloor && DN == nDirection) ||
        (TOP_FLOOR == nCurrFloor && UP == nDirection)) return nCurrFloor;

//    if ( nDirection == DN ) sleep( 64 );
    sleep( 64 );

    m_nPosition.X = ELEV_BOX;
    m_nPosition.Y = nIndex + nDirection;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );
    printf( ( TOP_FLOOR == nCurrFloor || BOT_FLOOR == nCurrFloor )
            ? ELEVATOR[HORZ_BAR].c_str() : ELEVATOR[SPACES].c_str() );

    nOffset = 0;
    nIndex  -= nDirection;

    for ( m_nPosition.Y = nIndex - 1; m_nPosition.Y < nIndex + 2; m_nPosition.Y++, nOffset++ )
    {
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( ( nOffset != 1 ) 
                  ? ELEVATOR[EL_TOP + nOffset].c_str() 
                  : ( bRider ) 
                      ? ELEVATOR[EL_RIDER].c_str() 
                      : ELEVATOR[EL_BODY].c_str() );
    }

    sleep( 160 );
    m_nPosition.Y = nIndex + nDirection;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );
    printf( ELEVATOR[SPACES].c_str() );

    nOffset = 0;
    nIndex  -= nDirection;

    for ( m_nPosition.Y = nIndex - 1; m_nPosition.Y < nIndex + 2; m_nPosition.Y++, nOffset++ )
    {
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( ( nOffset != 1 ) 
                  ? ELEVATOR[EL_TOP + nOffset].c_str() 
                  : ( bRider ) 
                      ? ELEVATOR[EL_RIDER].c_str() 
                      : ELEVATOR[EL_BODY].c_str() );
    }

    theElevator->setCurrFloor( nCurrFloor + nDirection );
    return nCurrFloor + nDirection;
}




