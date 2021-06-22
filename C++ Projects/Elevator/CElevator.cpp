     //////////////////////////////////////////////////
    //  Program: Elevator Sim : CElevator.cpp 
   //    Author: Victor Trucker
  //       Date: 06/22/2013
 // Description: Code for the 'Elevator' class
//////////////////////////////////////////////////
#include "CBuilding.h"

CElevator::CElevator( int nMaxFloor )
{
    m_hConsole    = GetStdHandle( STD_OUTPUT_HANDLE );
    m_nPosition.X = 0;
    m_nPosition.Y = 0;
    m_nCurrFloor  = 1;
    m_nMaxFloor   = nMaxFloor;
    m_nCallFloor  = 0;
    m_bRider      = false;
}


CElevator::~CElevator()
{
    ;
}


bool CElevator::openDoors( bool bOpen ) // true=open false=close
{
    int
        nIndex = (((m_nMaxFloor + 1) - m_nCurrFloor ) + 2) * 2;

    m_nPosition.X = ELEV_BOX - 1;
    m_nPosition.Y = nIndex;
    SetConsoleCursorPosition( m_hConsole, m_nPosition );

    printf( ( bOpen ) ? "  " : "³³" );
    sleep( 192 );

    return true;
}


void CElevator::sleep( unsigned int mseconds ) 
{ 
    clock_t goal = mseconds + clock();
    while ( goal > clock() );
}

