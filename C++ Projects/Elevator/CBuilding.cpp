     //////////////////////////////////////////////////
    //  Program: Elevator Sim : CBuilding.cpp 
   //    Author: Victor Trucker
  //       Date: 03/22/2021
 // Description: Code for the 'Building' class
//////////////////////////////////////////////////
#include "CBuilding.h"

CBuilding::CBuilding( int numFloors )
{
    srand( (unsigned)time(NULL) );

    m_hConsole    = GetStdHandle( STD_OUTPUT_HANDLE );
    m_nFinished   = 0;
    m_nPosition.X = 0;
    m_nPosition.Y = 0;
    m_nCurrFloor  = 1;
    m_nNumFloors  = numFloors;
    initBuilding();
}


CBuilding::~CBuilding()
{
    ;
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
    string
        nums[] = {"0","1","2","3","4","5","6","7","8","9"};

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

    // add the floor numbers
    for ( nLup = 1; nLup < m_nNumFloors; nLup++ )
    {
        int mx = m_nNumFloors - ( nLup - 1 );
        if ( mx > 9 ) mx -= 10;
        int wch = ( nLup > 9 ) ? ( nLup - 10 ) : nLup;
        string digOne = " ";
        if( m_nNumFloors - nLup >= 9 ) digOne = "1";
        string flrNumber = digOne + nums[mx];
        m_nPosition.X = 10;
        m_nPosition.Y = 4 + ( nLup * 2 );
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        cout << flrNumber;
    }

    return true;
}


void CBuilding::addElevator()
{
    int
        nOffset = 0,
        nIndex  = ((((m_nNumFloors + 1) - BOT_FLOOR) + 2) * 2) - 1;

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
        person->setIndex_Y( (((m_nNumFloors + 1) - BOT_FLOOR) + 2) * 2 );
        person->setActive( false );

        m_vecPeople.push_back( *person );

        delete person;
    }

  return true;
}


void CBuilding::openForBusiness()
{
    do
    {
        checkForRiders();
        m_nCurrFloor = moveElevatorToRoof( );
        m_nCurrFloor = moveElevatorToBase( );
    }
    while ( m_nFinished < MAX_PEOPLE );

    sleep( 500 );
}


void CBuilding::openDoor( bool bOpen ) // true=open false=close
{
    int
        nIndex = (((m_nNumFloors + 1) - m_nCurrFloor ) + 2) * 2;

    if ( bOpen )
    {
        sleep( 192 );
        m_nPosition.X = ELEV_BOX - 1;
        m_nPosition.Y = nIndex;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( PIECES[SPACE].c_str() );
        sleep( 192 );

        openElevatorDoor( OPEN );
    }
    else
    {
        openElevatorDoor( CLOSE );
        m_nPosition.X = ELEV_BOX - 1;
        m_nPosition.Y = nIndex;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        printf( PIECES[PIPE].c_str() );
        sleep( 192 );
    }
}


void CBuilding::openElevatorDoor( bool bOpen ) // true=open false=close
{
    int
        nIndex = (((m_nNumFloors + 1) - m_nCurrFloor ) + 2) * 2;

    m_nPosition.X = ELEV_BOX;
    m_nPosition.Y = nIndex;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );

    printf( ( bOpen ) ? " " : "³" );
    sleep( 192 );
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
        nIndex     = (((m_nNumFloors + 1) - BOT_FLOOR) + 2) * 2;

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
    openElevatorDoor( CLOSE );
    m_bDoorIsOpen = false;
}


int CBuilding::moveElevatorToRoof( )
{
    _vecPIt
        vecPItL;
    int
        nIndex = 0;
    char
        chName = ' ';

    do
    {
        m_nCurrFloor = moveElevatorOneFloor( m_nCurrFloor, UP );
        nIndex = (((m_nNumFloors + 1) - m_nCurrFloor) + 2) * 2;

        for ( m_vecPIt = m_vecPeople.begin(); m_vecPIt != m_vecPeople.end(); m_vecPIt++ )
        {
            chName = m_vecPIt->getName();

        if ( m_vecPIt->getRider() ) 
        {
            m_vecPIt->setCurrFloor( m_nCurrFloor );
        }

        if ( m_vecPIt->getCurrFloor() == m_nCurrFloor )
        {
            if ( m_vecPIt->getRider() && m_nCurrFloor == m_vecPIt->getDesiredFloor() )
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
                m_vecPIt->setCurrFloor( m_nCurrFloor );
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
    while ( m_nCurrFloor < m_nMaxFloor );

    return m_nCurrFloor;
}


int CBuilding::moveElevatorToBase( )
{
    _vecPIt
        vecPItL;
    int
        nIndex = 0;

    do
    {
        m_nCurrFloor = moveElevatorOneFloor( m_nCurrFloor, DN );
        nIndex = (((m_nNumFloors + 1) - m_nCurrFloor) + 2) * 2;

        for ( vecPItL = m_vecPeople.begin(); vecPItL != m_vecPeople.end(); vecPItL++ )
        {
            if ( vecPItL->getRider() && vecPItL->getDesiredFloor() == m_nCurrFloor )
            {
                vecPItL->setCurrFloor( m_nCurrFloor );
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

                vecPItL->setCurrFloor( m_nCurrFloor );

                if ( m_nCurrFloor == BOT_FLOOR && vecPItL->getIndex_X() >= WAITER )
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

        if ( BOT_FLOOR == m_nCurrFloor ) break;
    }
    while ( 1 );

    return BOT_FLOOR;
}


int CBuilding::moveElevatorOneFloor( int nFloor, int nDirection )
{
    int
        nOffset = 0,
        nIndex  = (((m_nNumFloors + 1) - m_nCurrFloor) + 2) * 2;
    _vecPIt
        vecPItL;
    bool
        bRider = verifyRider();

    if ((BOT_FLOOR == nFloor && DN == nDirection) ||
        (m_nNumFloors == nFloor && UP == nDirection)) return nFloor;

    sleep( 64 );

    m_nPosition.X = ELEV_BOX;
    m_nPosition.Y = nIndex + nDirection;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );
    printf( ( m_nNumFloors == m_nCurrFloor || BOT_FLOOR == m_nCurrFloor )
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

    m_nCurrFloor = nFloor + nDirection;
    return nFloor + nDirection;
}




