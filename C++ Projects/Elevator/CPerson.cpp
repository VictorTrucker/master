     //////////////////////////////////////////////////
    //  Program: Elevator Sim : CPerson.cpp 
   //    Author: Victor Trucker
  //       Date: 06/22/2015
 // Description: Code for the 'Person' class
//////////////////////////////////////////////////
#include "CBuilding.h"

CPerson::CPerson()
{
    srand( (unsigned)time(NULL) );

    m_hConsole       = GetStdHandle( STD_OUTPUT_HANDLE );
    m_nPosition.X    = 0;
    m_nPosition.Y    = 0;
    m_nTopFloor      = 0;
    m_nCurrFloor     = 1;
    m_nDesiredFloor  = 0;
    m_nIndex_X       = 16;
    m_nIndex_Y       = 0;
    m_bFinished      = false;
    m_bActive        = false;
    m_bWantsElevator = false;
    m_bRider         = false;
    m_bIsOneRider    = false;
}


CPerson::~CPerson()
{
    ;
}


void CPerson::sleep( unsigned int mseconds ) 
{ 
    clock_t goal = mseconds + clock();
    while ( goal > clock() );
}


bool CPerson::setWantsElevator( bool bWantsElevator )
{
    m_bWantsElevator = bWantsElevator;
    return m_bWantsElevator;
}


void CPerson::setDesiredFloor( int nCurrFloor )
{
    int
        nSelection = 0;

    do 
    {
        nSelection = rand() % m_nTopFloor + 1;
    }
    while ( nSelection == nCurrFloor );

    m_nDesiredFloor = nSelection;
}


void CPerson::callElevator()
{
    int
        nIndex = (((TOP_FLOOR + 1) - m_nCurrFloor ) + 2) * 2;

    m_nPosition.Y = nIndex;
    m_nPosition.X = (int)m_chName - 44;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );
    cout << PIECES[SPACE].c_str();

    m_nPosition.X = WAITER;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );
    cout << m_chName;
}


void CPerson::enterBuilding( bool bEnter )
{
    int
        nLup   = 0,
        nIndex = (((TOP_FLOOR + 1) - m_nCurrFloor ) + 2) * 2;

    m_nPosition.Y = nIndex;

    if ( bEnter )
    {
        m_nPosition.X = ENTRY;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        cout << m_chName;

        for ( nLup = ENTRY; nLup < WAITER; nLup++ )
        {
            sleep( 125 );
            m_nPosition.X = nLup;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << PIECES[SPACE].c_str();
            m_nPosition.X = nLup + 1;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << m_chName;
        }

        m_nIndex_X = m_nPosition.X;
        m_nIndex_Y = m_nPosition.Y;
    }
    else
    {
        m_nPosition.X = WAITER;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        cout << m_chName;

        for ( nLup = WAITER; nLup >= ENTRY; nLup-- )
        {
            sleep( 125 );
            m_nPosition.X = nLup;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << PIECES[SPACE].c_str();
            if ( nLup == ENTRY ) break;
            m_nPosition.X = nLup - 1;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << m_chName;
        }

        sleep( 125 );
        m_nPosition.X = nLup;
        SetConsoleCursorPosition( m_hConsole, m_nPosition );
        cout << " ";
        sleep( 125 );

        m_nIndex_X = m_nPosition.X;
        m_nIndex_Y = m_nPosition.Y;
    }
}


void CPerson::enterElevator( bool bEnter, bool bRiders )
{
    int
        nIndex = (((TOP_FLOOR + 1) - m_nCurrFloor ) + 2) * 2;

    m_bRider = bEnter; //true=enter=rider - false=exit=not rider
    m_nPosition.Y = nIndex;

    if ( bEnter )
    {
        for ( int nLup = WAITER; nLup < RIDER; nLup++ )
        {
            m_nPosition.X = nLup;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << PIECES[SPACE].c_str();
            m_nPosition.X = nLup + 1;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << m_chName;

            m_nIndex_X = m_nPosition.X;
            m_nIndex_Y = m_nPosition.Y;
            sleep( 125 );
        }
    }
    else
    {
        for ( int nLup = RIDER; nLup > WAITER; nLup-- )
        {
            m_nPosition.X = nLup;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            printf( ( nLup == RIDER && bRiders ) ? PIECES[PERSON].c_str() : PIECES[SPACE].c_str() );

            m_nPosition.X = nLup - 1;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << m_chName;

            m_nIndex_X = m_nPosition.X;
            m_nIndex_Y = m_nPosition.Y + 6;
            if ( nLup == WAITER ) break;
            sleep( 125 );
        }

        if ( m_nCurrFloor != 1 )
        {
            m_nPosition.X = WAITER;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << PIECES[SPACE].c_str();

            m_nPosition.X = (int)m_chName - 44;
            SetConsoleCursorPosition( m_hConsole, m_nPosition );
            cout << m_chName;
        }
    }
}


