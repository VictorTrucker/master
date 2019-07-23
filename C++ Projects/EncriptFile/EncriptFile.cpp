        //////////////////////////////////////////////////
       //  Program: Encript by Word                    //
      //    Author: Victor Trucker                    //
     //       Date: 06/08/2015                       //
    //   Copyright: 2015 all rights reserved        //
   //  Description: An implementation of the       //
  //                encription algorythm on       //
 //                 a larger scale               //
//////////////////////////////////////////////////

#include <stdlib.h>
#include <tchar.h>
#include <time.h>
#include <iostream>
#include <io.h>
#include <conio.h>
#include <fstream>
#include <vector>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

typedef vector<string> _vecS;
typedef _vecS::iterator _vecSIt;

const int UPPER     = 64; // @
const int LOWER     = 96; // .
const int MIN_SHIFT =  4;
const int UBOUND    =  3;

const string INVALID_CODE  = "\n\n\n\n\t  >>  INVALID CODE  <<\n\tPress any key to continue...";
const string FILE_ERROR_1  = "\n\n\n\t>> Unable to open ";
const string FILE_ERROR_2  = "\n\n\t  >> Failed creating file lists! There are <<\n\t  >> NO appropriate files in this folder!! <<\n\n";
const string NO_FILES      = "\n\nNo Files in This Directory!\n\n";
const string SELECTION     = "\n\t  >> Your Selection: ";
const string MAINMENU      = "\n\n\t  >> Main Menu <<\n\n";
const string FILEMENU      = "\n\n\t  >> Files to %s <<\n\n";
const string BACK_TO_MAIN  = "\t . = Main Menu\n";
const string MENU_LINE     = "\t %d = %s\n";
const string NEW_LINE      = "\n\0\0";
const string NEW_LINES     = " <<\n\n";
const string SPACE         = " ";
const string ENC           = ".enc\0";
const string DEC           = ".dec\0";

const string EXT[UBOUND]        = { "None",
                                    ".txt",
                                    ".enc" };

const string OPERATION[UBOUND]  = { "None",
                                    "Encript",
                                    "Decript" };

const string MAIN_MENU[UBOUND]  = { "\t 1 = Encript a File\n",
                                    "\t 2 = Decript a File\n",
                                    "\t . = Exit Program\n" };

char doMenu( _vecS & menu, string & strHeader );
void split( string & strText, const string & strSeperators, _vecS & words );
void saveFile( _vecS & vecFile, string & strFileName, bool bEncript );
bool loadFileMap( _vecS & vecText, _vecS & vecEncript );
void encriptFile( _vecS & vFiles, char chSelection, bool bEncript = true );
string encript( string & str, bool bEncript = true );


/////////////
int main()//
{//////////
  bool 
    bEncripting = true;
  char
    ch[256],
    chSelect1 = '\0',
    chSelect2 = '\0';
  _vecS
    mainMenu,
    txtFiles,
    encFiles;
  string
    strHeader = "";

  srand( (unsigned)time(NULL) );

  for ( int i = 0; i < UBOUND; i++ )
  {
    mainMenu.push_back( MAIN_MENU[i] );
  }

  do
  {
    if ( !loadFileMap( txtFiles, encFiles ) )
    {
      cerr << FILE_ERROR_2;
      _getch();
      exit( 1 );
    }

    strHeader = MAINMENU;

    system( "cls" );
    chSelect1 = doMenu( mainMenu, strHeader );
    system( "cls" );

    bEncripting = ( chSelect1 == '1' ) ? true : false;
    sprintf_s( ch, 256, FILEMENU.c_str(), OPERATION[chSelect1 - 48].c_str() );
    strHeader = ch;

    switch ( chSelect1 )
    {
    case '.': // Exit Program
      break;

    case '1': // Encript File
      chSelect2 = doMenu( txtFiles, strHeader );
      if ( chSelect2 != '.' )
        encriptFile( txtFiles, chSelect2, bEncripting );
      break;

    case '2': // Decript File
      chSelect2 = doMenu( encFiles, strHeader );
      if ( chSelect2 != '.' )
        encriptFile( encFiles, chSelect2, bEncripting );
      break;

    default:
      system( "cls" );
      cout << INVALID_CODE;
      flush( cout );
      _getch();
      break;
    };
  }
  while ( chSelect1 != '.' );

  return 0;
}


/////////////////////////////////////////////////////////////////////////////
void split( string & strText, const string& strSeperators, _vecS & words )//
{//////////////////////////////////////////////////////////////////////////
  int
    nTextLen = strText.length(),
    nStart   = strText.find_first_not_of( strSeperators, 0 );

  while ( ( nStart >= 0 ) && ( nStart < nTextLen ) )
  {
    int
      stop = strText.find_first_of( strSeperators, nStart + 1 );

    if ( ( stop < 0 ) || ( stop > nTextLen ) )
    {
      stop = nTextLen;
    }

    string
      str = strText.substr( nStart, stop - nStart );

    words.push_back( str );
    nStart = strText.find_first_not_of ( strSeperators, stop + 1 );
  } 
}


//////////////////////////////////////////////////
char doMenu( _vecS & menu, string & strHeader )//
{///////////////////////////////////////////////
  cout << strHeader;

  for ( _vecSIt _sit = menu.begin(); _sit != menu.end() ; _sit++ )
  {
    cout << (*_sit);
  }

  cout << SELECTION;

  flush( cout );
  return _getch();
}


//////////////////////////////////////////////////////////
bool loadFileMap( _vecS & vecText, _vecS & vecEncript )//
{///////////////////////////////////////////////////////
  bool
    bRet  = true;
  int
    nCnt1 = 0,
    nCnt2 = 0;
  char
    ch[256];
  long
    hFile;
  _finddata_t //struct
    fileList;
  string
    strFileName = "";


  if ( ( hFile = _findfirst( "*.*", &fileList ) ) == -1L )
  {
    system( "cls" );
    cerr << NO_FILES;
    exit( 1 );
  }

  vecText.clear();
  vecEncript.clear();

  do
  {
    strFileName = fileList.name;
    _strlwr_s( (char*)strFileName.c_str(), strFileName.length() + 1 );

    if ( strstr( fileList.name, EXT[1].c_str() ) != NULL )
    {
      nCnt1++;
      sprintf_s( ch, 256, MENU_LINE.c_str(), nCnt1, fileList.name );
      const string str = ch;
      vecText.push_back( str );
    }
    else if( strstr( fileList.name, EXT[2].c_str() ) != NULL )
    {
      nCnt2++;
      sprintf_s( ch, 256, MENU_LINE.c_str(), nCnt2, fileList.name );
      const string str = ch;
      vecEncript.push_back( str );
    }
  }
  while ( _findnext( hFile, &fileList ) == 0 );

  if ( nCnt1 + nCnt2 == 0 )
  {
    bRet = false;
  }

  _findclose( hFile );
  vecText.push_back( BACK_TO_MAIN );
  vecEncript.push_back( BACK_TO_MAIN );

  return bRet;
}


////////////////////////////////////////////////////////////////////////
void saveFile( _vecS & vecFile, string & strFileName, bool bEncript )//
{/////////////////////////////////////////////////////////////////////
  ofstream
    outFile;
  int
    nStart = strFileName.length() - 5;

  if ( bEncript )
  {
    strFileName.replace( nStart, 5, ENC );
  }
  else
  {
    strFileName.replace( nStart, 5, DEC );
  }

  outFile.open( strFileName.c_str(), ios::out );

  if ( !outFile.is_open() )
  {
    cerr << FILE_ERROR_1 << strFileName << NEW_LINES;
    _getch();
    exit( 1 );
  }

  for ( _vecSIt _sit = vecFile.begin(); _sit != vecFile.end(); _sit++ )
  {
    outFile << (*_sit);
  }

  outFile.close();
}


////////////////////////////////////////////////////////////////////////////////
void encriptFile( _vecS & vFiles, char chSelection, bool bEncript /*= true*/)//
{/////////////////////////////////////////////////////////////////////////////
  int
    nCnt = 0;
  string
    strText,
    strReturn,
    strEncLine,
    strFileName;
  _vecS
    vecEncFile,
    vecFileWords;
  _vecSIt
    _sit;

  nCnt++;

  for ( _vecSIt _fn = vFiles.begin(); _fn != vFiles.end(); _fn++ )
  {
    string
      fileName = (*_fn);
    char
      chTarget = fileName.c_str()[2];

    strFileName = fileName.substr( fileName.find( "=" ) + 2, fileName.length() - 4  );
    strFileName[strFileName.length() - 1] = '\0'; // vtt

    if ( chTarget == chSelection )
    {
      ifstream
        inFile( strFileName.c_str(), ios::in );

      if ( !inFile.is_open() )
      {
        cerr << FILE_ERROR_1 << strFileName << NEW_LINES;
        _getch();
        exit( 1 );
      }

      getline( inFile, strText );

      do
      {
        split( strText, SPACE, vecFileWords );

        for ( _sit = vecFileWords.begin(); _sit != vecFileWords.end(); _sit++ )
        {
          strReturn = encript( (*_sit), bEncript ) + SPACE;
          strEncLine += strReturn;
        }

        strEncLine += NEW_LINE;
        vecEncFile.push_back( strEncLine );
        vecFileWords.clear();
        strEncLine.clear();
      }
      while ( getline( inFile, strText ) );

      inFile.close();
      saveFile( vecEncFile, strFileName, bEncript );
    }
  }
}


///////////////////////////////////////////////////////////
string encript( string & str, bool bEncript /*= true*/ )//
{////////////////////////////////////////////////////////
  if ( str.empty() ) return str;

  int
    nIdx   = 1,
    rnd    = 0,
    nCnt   = 0,
    nShift = 0,
    jump   = 0,
    bump   = 0,
    step   = 0,
    nLen   = str.length(),
    ctl[2] = {UPPER, LOWER};
  char
    ch;
  string
    strSource = str,
    strResult = "";

  if ( bEncript )
  {
    rnd        = rand();
    jump       = ( rnd % 26 ) + 1;
    bump       = ( rnd % 2 );
    nShift     = ( rnd % 5 );
    step       = ( jump % 4 ) + 1;
    ch         = (char)( ctl[bump] + jump );
    strResult  = (char)ch;
    strResult += (char)( ctl[nIdx] + nShift + 1 );
  }
  else
  {
    nIdx      *= -1;
    ch         = str[0];
    ch         = toupper( ch );
    ch        -= ctl[0];
    step       = ( ch % 4 ) + 1;
    ch         = str[1];
    ch         = tolower( ch );
    nShift     = (int)( ( ch - ctl[1] ) - 1 );
    strSource  = str.substr( 2, str.length() );
    nLen      -= 2;
  }

  for ( nCnt = 0; nCnt < nLen; nCnt++, nShift++ )
  {
    ch         = strSource[nCnt];
    ch        += ( nIdx * nShift );
    strResult += (char)ch;
    nShift     = ( nShift == ( MIN_SHIFT + step ) ) ? 0 : nShift;
  }

  return strResult;
}

