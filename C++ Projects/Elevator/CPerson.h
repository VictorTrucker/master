      //////////////////////////////////////////////////
     //  Program: Elevator Sim : CPerson.h
    //    Author: Victor Trucker
   //       Date: 03/22/2021
  // Description: Person class for each rider
 //               setting and operation params
//////////////////////////////////////////////////
#pragma once

class CPerson  
{
public:
  CPerson();
  virtual ~CPerson();

public:
  void setCurrFloor( const int nValue )    { m_nCurrFloor = nValue;    };
  int  getCurrFloor( void )                { return m_nCurrFloor;      };
  void setIndex_X( const int nValue )      { m_nIndex_X = nValue;      };
  int  getIndex_X( void )                  { return m_nIndex_X;        };
  void setTopFloor( const int nValue )     { m_nTopFloor = nValue;     };
  int  getTopFloor( void )                 { return m_nTopFloor;       };
  void setIndex_Y( const int nValue )      { m_nIndex_Y = nValue;      };
  int  getIndex_Y( void )                  { return m_nIndex_Y;        };
  bool getWantsElevator( void )            { return m_bWantsElevator;  };
  int  getDesiredFloor( void )             { return m_nDesiredFloor;   };
  void setRider( const bool bValue )       { m_bRider = bValue;        };
  bool getRider( void )                    { return m_bRider;          };
  void setAnyRider( const bool bValue )    { m_bAnyRider = bValue;     };
  bool getAnyRider( void )                 { return m_bAnyRider;       };
  void setName( char chName )              { m_chName = chName;        };
  char getName( void )                     { return m_chName;          };
  void setActive( bool bActive )           { m_bActive = bActive;      };
  bool getActive( void )                   { return m_bActive;         };
  void setFinished( bool bFinished )       { m_bFinished = bFinished;  };
  bool getFinished( void )                 { return m_bFinished;       };
  void clearDesiredFloor( void )           { m_nDesiredFloor = 0;      };
  void clearWantsElevator( void )          { m_bWantsElevator = false; };
  void setIsOneRider( bool bRider )        { m_bRider = bRider;        };

  bool setWantsElevator( bool bWantsElevator );
  void setDesiredFloor( const int nCurrFloor );

  void callElevator( void );
  void enterBuilding( bool bEnter ); //true=enter false=exit
  void enterElevator( bool bEnter, bool bRiders ); //true=enter false=exit
  void sleep( unsigned int mseconds );

///////////////////////////////////////////////////////
protected:
  COORD
    m_nPosition;
  HANDLE
    m_hConsole;
  int
    m_nTopFloor,
    m_nIndex_X, // where on the floor
    m_nIndex_Y, // which floor
    m_nCurrFloor,
    m_nDesiredFloor;
  bool
    m_bActive,
    m_bIsOneRider,
    m_bFinished,
    m_bWantsElevator,
    m_bAnyRider,
    m_bRider;
  char
    m_chName;

};
