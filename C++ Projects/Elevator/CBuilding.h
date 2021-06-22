      //////////////////////////////////////////////////
     //  Program: Elevator Sim : CBuilding.h
    //    Author: Victor Trucker
   //       Date: 03/22/2021
  // Description: Building class for host container
 //               setting and operation params
//////////////////////////////////////////////////
#pragma once

#pragma warning( disable:4786 )
// support for windows functionality
#include <windows.h>
#include <iostream>
#include <conio.h>
#include <time.h>
// support for ansi standard c++ library
#include <stdlib.h>
#include <vector>
#include <string>
// my stuff
#include "CPerson.h"

using namespace std;

typedef vector<CPerson> _vecP;
typedef _vecP::iterator _vecPIt;

  ///////////////////////////////////////////////////////
 // define the constants for this interface
///////////////////////////////////////////////////////
// default empty parts for building
const int building1[] = { 0,1,2,3,4,6,7,8 };
// indicies into our pieces and elevator arrays for display purposes
const enum { ULHC, URHC, LLHC, LRHC, HORZ, VERT, PERSON, PIPE, SPACE };
const enum { SPACES, EL_TOP, EL_BODY, EL_FLOOR, EL_RIDER, HORZ_BAR };
// basic operational parameters
const bool OPEN         = true;
const bool CLOSE        = false;
const int DN            = -1;
const int UP            =  1;
const int BOT_FLOOR     =  1;
const int TOP_FLOOR     = 19; // limit 19
const int ELEV_BOX      = 16;
const int MAX_PEOPLE    = 16;
const int ENTRY         = 10;
const int WAITER        = 14;
const int RIDER         = 17;
// pieces to show and hide portions of the elevator
const string PIECES[] = { "É",   // 0 - ╔
                          "»",   // 1 - ╗
                          "È",   // 2 - ╚
                          "¼",   // 3 - ╝
                          "Í",   // 4 - ═
                          "º",   // 5 - ║
                          "@",   // 6 - @
                          "³",   // 7 - │
                          " " }; // 8 -  (space)
// the whole elevator with and without rider
const string ELEVATOR[] = { "   ",   // 0 - (spaces)
                            "ÉÍ»",   // 1 - ╔═╗
                            "³ º",   // 2 - │ ║
                            "ÈÍ¼",   // 3 - ╚═╝
                            "³@º",   // 4 - │@║
                            "ÍÍÍ" }; // 5 - ═══
// the base building - level 4 & 5 make up a mid-floor
const string BUILDING[] = { "\t ÉÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍ»\n",    // 0 " ╔════════════════════════════╗ "
                            "\tÉÊÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÊ»\n",   // 1 "╔╩════════════════════════════╩╗"
                            "\tº     Elevator  Simulation     º\n",   // 2 "║     Elevator  Simulation     ║"
                            "\tÈËÍÍÍÍÍËÍÍÍËÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍË¼\n",   // 3 "╚╦═════╦═══╦══════════════════╦╝"
                            "\t º     ³   º                  º\n",    // 4 " ║     │   ║                  ║ "
                            "\t ÌÍÍÍÍÍ¹   ÌÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍ¹\n",    // 5 " ╠═════╣   ╠══════════════════╣ "
                            "\tÉÊÍÍÍÍÍÊÍÍÍÊÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÊ»\n",   // 6 "╔╩═════╩═══╩══════════════════╩╗"
                            "\tº                              º\n",   // 7 "║                              ║"
                            "\tÈÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍÍ¼\n" }; // 8 "╚══════════════════════════════╝"

//////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// C H A R A C T E R   M A P //////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
// È É Ê Ë Ì Í Î ¹ º » ¼ © ª ³ ´ Ù Ú â ¿ À Á Â Ã Ä Å µ ¶ · ¸ ½ ¾ Æ Ç Ï Ð Ñ Ò Ó Ô Õ Ö × Ø
//
// ╚ ╔ ╩ ╦ ╠ ═ ╬ ╣ ║ ╗ ╝ ⌐ ¬ │ ┤ ┘ ┌ Γ ┐ └ ┴ ┬ ├ ─ ┼ ╡ ╢ ╖ ╕ ╜ ╛ ╞ ╟ ╧ ╨ ╤ ╥ ╙ ╘ ╒ ╓ ╫ ╪
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////


class CBuilding
{
public:
    CBuilding( int numFloors );
    virtual ~CBuilding();

public:
    void initBuilding( void );
    bool createBuilding( void );
    void addElevator( void );
    bool spawnPeople( void );
    void openForBusiness( void );
    void verifyFloor( void );
    bool verifyRider( void );
    void openDoor( bool bOpen );
    void openElevatorDoor( bool bOpen );
    void sleep( unsigned int mseconds );
    void checkForRiders( void );
    int  moveElevatorToRoof( );
    int  moveElevatorToBase( );
    int  moveElevatorOneFloor( int nFloor, int direction );

    void setCurrFloor( int nFloor ) { m_nCurrFloor = nFloor; };
    int  getCurrFloor( void )       { return m_nCurrFloor;   };
    void setNumActive( int nValue ) { m_nNumActive = nValue; };
    int  getNumActive( void )       { return m_nNumActive;   };

///////////////////////////////////////////////////////
protected:
    COORD
        m_nPosition;
    HANDLE
        m_hConsole;
    bool
        m_bDoorIsOpen;
    int
        m_nFinished,
        m_nNumActive,
        m_nNumFloors,
        m_nMinFloor,
        m_nMaxFloor,
        m_nCurrFloor;
    _vecP
        m_vecPeople;
    _vecPIt
        m_vecPIt;
};

