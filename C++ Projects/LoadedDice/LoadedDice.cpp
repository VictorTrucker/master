         /////////////////////////////////////////////////////
        //  Program: Loaded Dice : LoadedDice.cpp
       //    Author: Victor Trucker
      //       Date: 09/30/2013
     // Description: loaded dice throwing program
    //               reading favored face (load),
   //                the degree of favoritism and
  //                 the number of throws to execute
 //                  from a human readable file
/////////////////////////////////////////////////////
#include "CDice.h"
#include <iostream>
#include <io.h>
#include <conio.h>
#include <fstream>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

const char * MESSAGE_FILE_ERR  = "\n\n\t >> Unable to open parameters.txt\n\n\t >> Press any key to exit...";
const char * MESSAGE_DONE      = "\n\n\t >> Done...\n\n\t >> Press any key to exit...";
const char * MESSAGE_LOADED    = "\n\n\t >> The Dice have been Loaded\n\n\t >> Press any key to continue...";
const char * TITLE_LOADED_DICE = "\n\n\t >> Let's throw some loaded dice...\n\n\t >> Some basic information about the dice:\n";
const char * DIE_PARAMETERS    = "\n\t >> Die Number %d, Favored Side = %d, Weight of Favor = %d\n";
const char * DIE_THROWS        = "\n\t Throw Number: %2d :: Die 1 Value = %d :: Die 2 Value = %d\n";

  ///////////////////////////////////////////////////////
 // define the functions for this interface
///////////////////////////////////////////////////////
int  getParams(CDice & die_1, CDice & die_2);
void throwDice(CDice & die_1, CDice & die_2, int numThrows);
void drawLine(void);

int main()
{
  int
    nVal    = 0,
    nThrows = 0;
  CDice
    die_01,
    die_02;
  char
    szOutput[_MAX_PATH];

  ///////////////////////////////////////////////////////
 // assign some basic values so we know who we are
///////////////////////////////////////////////////////
  nThrows = getParams(die_01, die_02);

  ///////////////////////////////////////////////////////
 // begin output of information about the dice
///////////////////////////////////////////////////////
  system("cls");
  cout << MESSAGE_LOADED;
  _getch();
  system("cls");

  cout << TITLE_LOADED_DICE;
  drawLine();
  sprintf_s(szOutput, DIE_PARAMETERS, 1, die_01.getFavor(), die_01.getWeight());
  cout << szOutput;
  sprintf_s(szOutput, DIE_PARAMETERS, 2, die_02.getFavor(), die_02.getWeight());
  cout << szOutput;
  drawLine();

  ///////////////////////////////////////////////////////
 // let the fun begin - throw the dice
///////////////////////////////////////////////////////
  throwDice(die_01, die_02, nThrows);

  drawLine();
  cout << MESSAGE_DONE;
  _getch();

  return 0;
}


int getParams(CDice & die_1, CDice & die_2)
{
  int
    nCnt    = 0,
    nVal    = 0,
    nThrows = 0;
  fstream
    infoFile;
  string
    strParameter;

  infoFile.open("parameters.txt", ios::in);
  ///////////////////////////////////////////////////////
 // if we cannot access the parameters file we bail
///////////////////////////////////////////////////////
  if (!infoFile.is_open())
  {
    cerr << MESSAGE_FILE_ERR;
    _getch();
    exit(1);
  }
  ///////////////////////////////////////////////////////
 // get the first line fo the file
///////////////////////////////////////////////////////
  getline(infoFile, strParameter);

   ///////////////////////////////////////////////////////
  // extract the meaningful data in this case the last 
 //  two characters of the const char*
///////////////////////////////////////////////////////
  do
  {
    const char
      *chValue = strParameter.c_str();
    chValue += ((int)strlen(chValue) - 2);
    nVal = atoi(chValue);
  ///////////////////////////////////////////////////////
 // assign our values to their respective containers
///////////////////////////////////////////////////////
    switch (nCnt)
    {
    case 0:
      die_1.setFavor(nVal); break;
    case 1:
      die_1.setWeight(nVal); break;
    case 2:
      die_2.setFavor(nVal); break;
    case 3:
      die_2.setWeight(nVal); break;
    case 4:
      nThrows = nVal; break;
    default:
      break;
    }

    nCnt++;
  } while (getline(infoFile, strParameter));

  ///////////////////////////////////////////////////////
 // close our parameters input file
///////////////////////////////////////////////////////
  infoFile.close();
  ///////////////////////////////////////////////////////
 // this just saves time and a couple cycles
///////////////////////////////////////////////////////
  return nThrows;
}


void throwDice(CDice & die_1, CDice & die_2, int numThrows)
{
  int
    nVal1 = 0,
    nVal2 = 0,
    nFav1 = die_1.getFavor(),
    nFav2 = die_2.getFavor();
  char
    szOutput[_MAX_PATH];

    ///////////////////////////////////////////////////////
   // loop through our throws and if the throw returns one 
  //  of the face values above 6, replace it with our
 //   favored face value
///////////////////////////////////////////////////////
  for (int nLup = 1; nLup <= numThrows; nLup++)
  {
    nVal1 = die_1.throwDice();
    nVal1 = (nVal1 > 6 ? nFav1 : nVal1);

    nVal2 = die_2.throwDice();
    nVal2 = (nVal2 > 6 ? nFav2 : nVal2);
  ///////////////////////////////////////////////////////
 // output our data
///////////////////////////////////////////////////////
    sprintf_s(szOutput, DIE_THROWS, nLup, nVal1, nVal2);
    cout << szOutput;
  }
}


void drawLine()
{
  cout << "\n\t ";
  for (int nLup = 0; nLup < 27; nLup++) cout << "==";
  cout << "\n";
}