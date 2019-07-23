      //////////////////////////////////////////////////
     //  Program: Elevator Sim : CElevator.h
    //    Author: Victor Trucker
   //       Date: 06/22/2015
  // Description: Elevator class used by host 
 //               setting and operation params
//////////////////////////////////////////////////
#pragma once

class CElevator  
{
public:
  CElevator();
  virtual ~CElevator();

public:
  void setRider( const bool bValue )          { m_bRider = bValue;     };
  int  getRider( void )                       { return m_bRider;       };
  void setCurrFloor( int nFloor )             { m_nCurrFloor = nFloor; };
  int  getCurrFloor( void )                   { return m_nCurrFloor;   };
  void setCallFloor( int nFloor )             { m_nCallFloor = nFloor; };
  int  getCallFloor( void )                   { return m_nCallFloor;   };
  void sleep( unsigned int mseconds );

  bool openDoors( bool bOpen ); //true=open false=close

///////////////////////////////////////////////////////
protected:
  COORD
    m_nPosition;
  HANDLE
    m_hConsole;
  int
    m_nCurrFloor,
    m_nCallFloor;
  bool
    m_bRider;

};
