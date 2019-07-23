      //////////////////////////////////////////////////
     //  Program: Loaded Dice : CDice.cpp 
    //    Author: Victor Trucker
   //       Date: 09/30/2013
  // Description: Code for Loaded Dice class for
 //               settings and throw operation
//////////////////////////////////////////////////
#include "CDice.h"
#include <stdlib.h>
#include <time.h>

CDice::CDice()
{
  m_nFavor  = 0;
  m_nWeight = 0;

  srand((unsigned)time(NULL));
}


CDice::~CDice()
{
  ;
}


void CDice::clearDice()
{
  m_nFavor  = 0;
  m_nWeight = 0;
}

      ////////////////////////////////////////////////////////////////////
     // we throw the dice here and add the weight for the favored face
    //  the intention is to assume a 6 sided die, and add additional
   //   sides to influence the roll.  1-6 still represent 1-6 and 
  //    the additional sides are dedicated to the favored face to
 //     give it the advantage through additional oportunities.
////////////////////////////////////////////////////////////////////
int CDice::throwDice()
{
  int
    nVal    = 0,
    nValue  = 0;

  nVal   = rand();
  nValue = (nVal % (6 + m_nWeight)) + 1;

  return nValue;
}

