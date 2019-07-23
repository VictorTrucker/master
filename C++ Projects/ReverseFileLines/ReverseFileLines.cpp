
  //////////////////////////////////////////
 /// Reverse the Lines in a File //////////
//////////////////////////////////////////

#include <iostream>
#include <conio.h>
#include <fstream>
#include <vector>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

typedef vector<string>             vec_Str;
typedef vec_Str::reverse_iterator _vec_Str;

int main()
{
  string
    srcFile = "infile.txt",
    tgtFile = "reversed.txt";
  ifstream
    infile(srcFile.c_str());
  ofstream
    outFile(tgtFile.c_str());

  system("cls");

  if (!infile.is_open())
  {
    cerr << "\n\n\t >> Unable to open " << srcFile << " <<\n\n";
  }
  else if (!outFile.is_open())
  {
    cerr << "\n\n\t >> Unable to open " << tgtFile << " <<\n\n";
  }
  else
  {
    vec_Str
      vecLines;
    _vec_Str 
      vecRevIter;
    string
      strFileLine = "";

    getline(infile, strFileLine);

    do
    {
      strFileLine += '\n';
      vecLines.push_back(strFileLine);
    }
    while (getline(infile, strFileLine));

    for (vecRevIter = vecLines.rbegin(); vecRevIter != vecLines.rend(); vecRevIter++)
    {
      strFileLine = (*vecRevIter);
      outFile.write(strFileLine.c_str(), strFileLine.length());
    }

    infile.close();
    outFile.close();
    
    cout << "\n\n\t >> Done ... Press any key to exit ...";
  }

  flush( cout );
  _getch();

  return 0;
}

