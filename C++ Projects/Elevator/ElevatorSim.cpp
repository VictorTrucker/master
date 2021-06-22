        /////////////////////////////////////////////////////
       //  Program: Elevator Sim : ElevatorSim.cpp
      //    Author: Victor Trucker
     //       Date: 03/22/2021
    // Description: Elevator simulation program with
   //               a callable elevator,
  //                queryable passengers,
 //                 variable building height
/////////////////////////////////////////////////////
#include "CBuilding.h"
#include <windows.h>

  ///////////////////////////////////////////////////////
 // define the functions for this interface
///////////////////////////////////////////////////////
void setcursor( bool, DWORD );
void displayMessage( int nFlr, string & strMessage );


int main()
{
    int
        nDef      = 14,
        nFlr      = 0;
    string
        strRed    = "Color 0c", // red on black
        strYellow = "Color 0e", // yellow on black
        strBlue   = "Color 09", // blue on black
        strTeal   = "Color 0b", // teal on black
        strColor  = strRed,     // we'll use red on black
        strMsg    = "";

    do {
        system("mode con cols=48 lines=6");
        system("title [ Elevator Simulation ]");
        cout << endl << endl;
        cout << " >> Enter number of floors (3 to 19): ";
        cin >> nFlr;

        if (cin.fail())
        {
           cin.clear();
           char c;
           cin >> c;
           nFlr = 0;
        }

        if ( nFlr == 0 ) nFlr = TOP_FLOOR;

        // Set CMD Window Size to fit building
        char szfn[32];
        int rows = (nFlr * 2) + nDef;
        sprintf_s( szfn, 32, "mode con cols=48 lines=%d", rows );
        system(szfn);
        system("cls");
    } while ( nFlr < 3 || nFlr > TOP_FLOOR );

// clear the screen and hide the cursor
    system( "cls" );
    system( strColor.c_str() );
    setcursor( 0, 0 );
// create and display the building
    CBuilding * theBuilding = new CBuilding( nFlr );
// open the building for business
    system("title [ Elevator Simulation - Running ]");
    theBuilding->openForBusiness();
    system("title [ Elevator Simulation - Done ]");

    strMsg = " Done... Press ANY Key to Exit. ";
    displayMessage( nFlr, strMsg );
    delete theBuilding;
    _getch();

    return 0;
}


void displayMessage( int nFlr, string & strMessage )
{
    int 
        nLen = strMessage.length();
    string
        strHORZ_BAR( nLen, 'Í' );
    HANDLE 
        console = GetStdHandle( STD_OUTPUT_HANDLE );
    COORD 
        newPosition = { 7, ((nFlr + 2) * 2) + 5 };

      SetConsoleCursorPosition( console, newPosition );
      cout << PIECES[ULHC] << strHORZ_BAR << PIECES[URHC];
      newPosition.Y++;
      SetConsoleCursorPosition( console, newPosition );
      cout << PIECES[VERT] << strMessage << PIECES[VERT];
      newPosition.Y++;
      SetConsoleCursorPosition( console, newPosition );
      cout << PIECES[LLHC] << strHORZ_BAR << PIECES[LRHC];
}


void setcursor( bool visible, DWORD size ) // visible :: 0 = invisible, 1 = visible
{
    if( size == 0 )
    {
        size = 20;
    }

    HANDLE 
        console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_CURSOR_INFO
        lpCursor;

    lpCursor.bVisible = visible;
    lpCursor.dwSize   = size;

    SetConsoleCursorInfo( console, &lpCursor );
}

