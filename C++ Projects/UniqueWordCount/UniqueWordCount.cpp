
  //////////////////////////////////////////////
 /// Unique Word Count ////////////////////////
//////////////////////////////////////////////

#include <iostream>
#include <conio.h>
#include <fstream>
#include <map>
#include <vector>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

typedef map<string, int> _map_;
typedef _map_::iterator  _map_iter;
typedef vector<string>   _vec_;
typedef _vec_::iterator  _vec_iter;

const char * HEADER_TEXT = "\t\t    WORD  COUNT\n\n\t  Word\t\t\t\tCount";
const char * PUNCTUATION = ".,?!;:";

void drawLine( void );
void split( _vec_ & vecString, string & strSource, char chDelimiter = ' ' );

int main()
{
  string
    strFileName = "infile.txt";
  fstream
    inFile;

  inFile.open( strFileName.c_str(), ios::in );

  if (!inFile.is_open())
  {
    cerr << "Unable to open input file " << strFileName << " Press any key to exit ... ";
  }
  else
  {
    _map_
      mapCount;
    _map_iter
      mapIter;
    _vec_
      vecWords;
    _vec_iter
      vecIter;
    string
      strWord     = "",
      strFileLine = "";
    int
      nLength = 0,
      nSpacer = 0;

    vecWords.clear();
    getline( inFile, strFileLine );

    do
    {
      split( vecWords, strFileLine );
    }
    while ( getline( inFile, strFileLine ) );

    inFile.close();

    for ( vecIter = vecWords.begin(); vecIter != vecWords.end(); vecIter++ )
    {
      mapIter = mapCount.find( (*vecIter) );

      if ( (*vecIter) == "" ) continue;

      if(mapIter == mapCount.end())
      {
        mapCount.insert( _map_::value_type( (*vecIter), 1 ) );
      }
      else
      {
        (*mapIter).second++;
      }
    }

    drawLine();
    cout << HEADER_TEXT;
    drawLine();

    for ( mapIter = mapCount.begin(); mapIter != mapCount.end(); mapIter++ )
    {
      strWord = (*mapIter).first;
      nLength = strWord.length();
      nSpacer = 32 - nLength;
      strWord.insert( nLength, nSpacer, ' ' );
      cout << "\n\t " << strWord << (*mapIter).second << endl;
    }

    drawLine();

    cout << "\n\n\t >> Done ... Press any key to exit ... ";
  }

  flush( cout );
  _getch();

  return 0;
}


void split(_vec_ & vecString, string & strSource, char chDelimiter)
{
  int
    nSize  = strSource.length() - 1,
    nStart = 0,
    nStop  = 0,
    nPos   = 0;
  string
    str    = strSource + chDelimiter,
    strAdd = "";

  do
  {
    nStop  = str.find_first_of( chDelimiter, nStart );
    strAdd = str.substr( nStart, nStop - nStart );
    nPos   = strAdd.find_first_of( PUNCTUATION );

    if ( nPos != string::npos && strAdd != "..." )
    {
      strAdd.replace( nPos, 1, "" );
    }

    vecString.push_back( strAdd );
    nStart = nStop + 1;
  }
  while ( nStop < nSize );
}


void drawLine()
{
  cout << endl << '\t';
  for ( int nLup = 0; nLup < 19; nLup++ ) cout << "==";
  cout << endl;
}

