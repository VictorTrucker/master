      //////////////////////////////////////////////////
     //  Program: Loaded Dice : CDice.h
    //    Author: Victor Trucker
   //       Date: 09/30/2013
  // Description: Loaded Dice class for each die
 //               setting and operation params
//////////////////////////////////////////////////
#pragma once

class CDice  
{
public:
  CDice();
  virtual ~CDice();

public:
  void clearDice(void);
  int  throwDice(void);
  void setFavor(const int nValue)  {m_nFavor = nValue;};
  int  getFavor(void)              {return m_nFavor;};
  void setWeight(const int nValue) {m_nWeight = nValue;};
  int  getWeight(void)             {return m_nWeight;};

  ///////////////////////////////////////////////////////
 // protect our data members
///////////////////////////////////////////////////////
protected:
  int
    m_nFavor,
    m_nWeight;
};
