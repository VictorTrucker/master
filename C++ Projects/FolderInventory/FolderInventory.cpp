          ////////////////////////////////////////////////////////////
         //  Program: Folder Inventory : FolderInventory.cpp
        //    Author: Victor Trucker
       //       Date: 10/02/2013
      // Description: get start folder from user input -
     //               walk the directory tree accumulating
    //                size total of the files in the start
   //                 folder and size total of the files in
  //                  all of its sub-folders (thread-safe)
 //                   (option: filter by '.exe' and '.dll')
////////////////////////////////////////////////////////////
#include <iostream>
#include <io.h>
#include <direct.h>
#include <conio.h>
#include <fstream>
#include <stdlib.h>
#include <string>
#include <windows.h>
#include <stdio.h>

using namespace std;
#pragma warning( disable:4786 )

const string DOTS[] = {":", ".", ".."};
HANDLE ghMutex; 

void drawLine(void);
void processDirectory(unsigned __int64 & nSubsTotalBytes, string & strNextFolder);

int main()
{
  long
    hFile = 0;
  unsigned __int64
    nBaseTotalBytes = 0,
    nSubsTotalBytes = 0;
  char
    szHomeFolder[_MAX_PATH],
    szStartFolder[_MAX_PATH];
  string
    strFileName = "";
  _finddata_t
    fileList;

  _getcwd(szHomeFolder, _MAX_PATH);

  ghMutex = CreateMutex(NULL, FALSE, NULL);

  if (ghMutex == NULL) 
  {
    cout << "\n\n\t >> Unable to create mutex\n\n\t >> Press any key to exit...";
  }
  else
  {
    system("cls");
    cout << "\n\n\t >> Please input a directory value : ";
    cin >> szStartFolder;
    system("cls");

    if (_chdir(szStartFolder) != 0)
    {
      cout << "\n\n\t >> Unable to open directory location\n\n\t >> Press any key to exit...";
    }
    else
    {
      string
        strCurrFolder = string(szStartFolder);

      if ((hFile = _findfirst("*.*", &fileList)) != -1L)
      {
        do
        {
          strFileName = fileList.name;

          if (fileList.attrib == _A_SUBDIR)
          {
            if (strFileName != DOTS[1] && strFileName != DOTS[2])
            {
              processDirectory(nSubsTotalBytes, strFileName);
            }
          }
          else
          {
// reinstate these two lines and the two below to filter by .exe and .dll
//            if (strstr(fileList.name, ".EXE") != NULL || strstr(fileList.name, ".exe") != NULL ||
//                strstr(fileList.name, ".DLL") != NULL || strstr(fileList.name, ".dll") != NULL)
            nBaseTotalBytes += fileList.size;
          }
        } while (_findnext(hFile, &fileList) == 0);

        _findclose( hFile );
      }

      drawLine();
      cout << "\n\t >> Total size of files in nominated folder   = " << nBaseTotalBytes << endl;
      cout << "\n\t >> Total size of files in nom's sub-folders  = " << nSubsTotalBytes << endl;
      drawLine();
      cout << "\n\n\t >> Done...\n\n\t >> Press any key to exit...";
    }
  }

  CloseHandle(ghMutex);
  _chdir(szHomeFolder);
  _getch();
  return 0;
}


void processDirectory(unsigned __int64 & nSubsBytes, string & strNextFolder)
{
  DWORD
    dwWaitResult = WaitForSingleObject(ghMutex, INFINITE);

  long
    hFile = 0;
  _finddata_t
    fileList;

  _chdir(strNextFolder.c_str());

  if ((hFile = _findfirst("*.*", &fileList)) != -1L)
  {
    do
    {
      string
        strFileName = fileList.name;

      if (fileList.attrib == _A_SUBDIR)
      {
        if (strFileName != DOTS[1] && strFileName != DOTS[2])
        {
          processDirectory(nSubsBytes, strFileName);
        }
      }
      else
      {
// reinstate these two lines and the two above to filter by .exe and .dll
//        if (strstr(fileList.name, ".EXE") != NULL || strstr(fileList.name, ".exe") != NULL ||
//            strstr(fileList.name, ".DLL") != NULL || strstr(fileList.name, ".dll") != NULL)
        nSubsBytes += fileList.size;
      }
    } while (_findnext(hFile, &fileList) == 0);

    _findclose( hFile );
    _chdir(DOTS[2].c_str());
  }

  ReleaseMutex(ghMutex);
}


void drawLine()
{
  DWORD
    dwWaitResult = WaitForSingleObject(ghMutex, INFINITE);

  cout << endl << '\t';
  for (int nLup = 0; nLup < 30; nLup++) cout << "==";
  cout << endl;

  ReleaseMutex(ghMutex);
}

