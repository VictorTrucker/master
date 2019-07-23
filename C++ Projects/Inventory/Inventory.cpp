         ////////////////////////////////////////////////////////////////////
        //  Program: Inventory : Inventory.cpp
       //    Author: Victor Trucker
      //       Date: 7/16/2019
     // Description: Add Vehicles to an Inventory -
    //               Allow Adding and Removing Vehicles to Inventory 
   //                Allow outputting ALL Vehicles in Inventory 
  //                 Allow outputting Vehicles by Year 
 //                  Allow outputting Vehicles by Manufacturer 
////////////////////////////////////////////////////////////////////
#include <stdlib.h>
#include <conio.h>
#include <time.h>
#include <windows.h>
#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;
#pragma warning( disable:4786 )

const char* CLR = "cls";

  ///////////////////////////////////////////////////////////////
 // BEGIN class CManufacturer Definitions
///////////////////////////////////////////////////////////////
class CManufacturer
{
public:
  CManufacturer();
  CManufacturer( string _name, string _country );
  virtual ~CManufacturer();

public:
  void print( void );
  string getName( void );
  void setName( string );
  string getCountry( void );
  void setCountry( string );

private:
  string
    m_name,
    m_country;
};


CManufacturer::CManufacturer( )
{
  ;
}


CManufacturer::CManufacturer( string _name, string _country )
{
  m_name    = _name;
  m_country = _country;
}


CManufacturer::~CManufacturer()
{
  ;
}


void CManufacturer::setName( string _name )
{
  m_name = _name;
}


string CManufacturer::getName( void )
{
  return m_name;
}


void CManufacturer::setCountry( string _country )
{
  m_country = _country;
}


string CManufacturer::getCountry( void )
{
  return m_country;
}


void CManufacturer::print()
{
  cout << " " << getName();
  cout << " " << getCountry();
}
  ///////////////////////////////////////////////////////////////
 // END class CManufacturer Definitions
///////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////
 // BEGIN class CAutomobile Definitions
///////////////////////////////////////////////////////////////
class CAutomobile : public CManufacturer
{
public:
  CAutomobile();
  CAutomobile( string _manufacturer, string _country, string _vin, string _model, string _color, string _year );
  virtual ~CAutomobile();

public:
  void print( void );
  string getVin( void );
  void setVin( string );
  string getModel( void );
  void setModel( string );
  string getColor( void );
  void setColor( string );
  string getYear( void );
  void setYear( string );

private:
  string
    m_vin,
    m_model,
    m_color,
    m_year;
};


CAutomobile::CAutomobile( )
  : CManufacturer( )
  , m_vin( "" )
  , m_model( "" )
  , m_color( "" )
  , m_year( "" )
{
  ;
}


CAutomobile::CAutomobile( string _manufacturer, string _country, string _vin, string _model, string _color, string _year )
  : CManufacturer( _manufacturer, _country )
  , m_vin( _vin )
  , m_model( _model )
  , m_color( _color )
  , m_year( _year )
{
  ;
}


CAutomobile::~CAutomobile()
{
  ;
}


void CAutomobile::setVin( string _vin )
{
  m_vin = _vin;
}


string CAutomobile::getVin( void )
{
  return m_vin;
}


void CAutomobile::setModel( string _model )
{
  m_model = _model;
}


string CAutomobile::getModel( void )
{
  return m_model;
}


void CAutomobile::setColor( string _color )
{
  m_color = _color;
}


string CAutomobile::getColor( void )
{
  return m_color;
}


void CAutomobile::setYear( string _year )
{
  m_year = _year;
}


string CAutomobile::getYear( void )
{
  return m_year;
}


void CAutomobile::print()
{
  string
    strPS   = "",
    strName = getName();

  if ( strName == "BMW" )
  {
    strPS = " (c) BMW AG, Munich Germany";
  }
  else if ( strName == "Tesla" )
  {
    strPS = " (Batteries Included!)";
  }

  cout << "\n\n " << getColor();
  cout << " " << getYear();
  cout << " " << getName();
  cout << " " << getModel();
  cout << " " << getVin();
  cout << " " << getCountry();
  cout << " " << strPS;
}
  ///////////////////////////////////////////////////////////////
 // END class CAutomobile Definitions
///////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////
 // std library type definitions for the application
///////////////////////////////////////////////////////////////
typedef map<string, CAutomobile> map_Inventory;
typedef map_Inventory::iterator iter_map_Inventory;
typedef pair<string, CAutomobile> map_Pair;

  ///////////////////////////////////////////////////////////////
 // Our storage map for Inventory data - map VIN to Automobile
///////////////////////////////////////////////////////////////
map_Inventory Inventory;

  ///////////////////////////////////////////////////////////////
 // Function Declarations
///////////////////////////////////////////////////////////////
int mainMenu( void );
string getVin( void );
bool verifyVIN( string _vinNumber );
string getColor( void );
string getYear( void );
string getModel( void );
string getManufacturer( void );
string getCountry( string _make );
void drawLine( void );
void buildInventory( void );
void addVehicleToInventory( void );
void removeVehicleFromInventory( string _vinNumber );
void displayAllVehicles( void );
void displayVehiclesByMake( string _manufacturer );
void displayVehiclesByYear( string _year );
string getField( const char* chPrompt, const char* chMask, const char* chMaskDesc );

  ///////////////////////////////////////////////////////////////
 // Usefull stuff for functionality
///////////////////////////////////////////////////////////////
const string MAKES[]  = {
  "General Motors",
  "Chevrolet",
  "Pontiac",
  "Buick",
  "Chrysler",
  "Plymouth",
  "Dodge",
  "Loncoln",
  "Mercury",
  "Ford",
  "Tesla",
  "Toyota",
  "Lexus",
  "Nissan",
  "Infinity",
  "Honda",
  "Acura",
  "BMW",
  "Mercedes Benz",
  "Porsche",
  "Hyundai",
  "Kia"
};


      ///////////////////////////////////////////////////////////////
     // Main Driver for the application
    //       Arguments: None
   //           Return: Integer Exit code
  //  Launch the input menu requesting user input and respond 
 //   accordingly by calling the appropriate functions.
///////////////////////////////////////////////////////////////
int main()
{
  int
    nYear      = 0,
    nSelection = 0;
  string
    strMake    = "",
    strYear    = "",
    strVin     = "";
  HWND
    console    = GetConsoleWindow();
  RECT
    ConsoleRect;

  srand( (unsigned)time( NULL ) );
  GetWindowRect(console, &ConsoleRect); 
  MoveWindow(console, ConsoleRect.left, ConsoleRect.top, 575, 620, true);

  do
  {
    bool
      bFound   = false;

    nSelection = mainMenu();

    switch( nSelection )
    {
    case 1: // Let the Computer Build the Inventory
      buildInventory();
      break;
    case 2: // Add a Vehicle to the Inventory
      addVehicleToInventory();
      break;
    case 3: // Remove a Vehicle from the Inventory
      strVin = getVin();
      removeVehicleFromInventory( strVin );
      break;
    case 4: // Display ALL the Vehicles
      displayAllVehicles();
      break;
    case 5: // Display the Vehicles by Yesr
      strYear = getYear();
      displayVehiclesByYear( strYear );
      break;
    case 6: // Display the Vehicles by Manufacturer
      strMake = getManufacturer();
      displayVehiclesByMake( strMake );
      break;
    case 7: // Exit the Application
      clock_t tstart = clock();
      clock_t tend   = clock();
      cout << "\n\n     Exiting Application - Good-Bye ...";

      do
      {
        tend = clock();
      } while ( (tend - tstart) / (double) CLOCKS_PER_SEC < 2.0 );

      return 0;
    }
  } while( 1 );
}


      ///////////////////////////////////////////////////////////////
     // Display the Main Menu for Collecting User Input
    //       Arguments: None
   //           Return: Integer strRet, user selection to return
  //  Display the input menu requesting user input and 
 //   respond with the users selection.
///////////////////////////////////////////////////////////////
int mainMenu( void )
{
  string
    strRet = "";

  system( CLR );
  cout << endl;
  drawLine();
  cout << "        >> Menu of Choices <<" << endl;
  drawLine();
  cout << "  1) =  Let the Computer Build the Inventory\n"  << endl;
  cout << "  2) =  Add a Vehicle to the Inventory\n"  << endl;
  cout << "  3) =  Remove a Vehicle from the Inventory\n"  << endl;
  cout << "  4) =  Display All of the Vehicles in Inventory\n"  << endl;
  cout << "  5) =  Display Vehicles by Year\n"  << endl;
  cout << "  6) =  Display Vehicles by Manufacturer\n"  << endl;
  cout << "  7) =  Quit and Exit the Application"  << endl;
  drawLine();
  cout << "        >> Input Your Selection: ";

  flush( cout );
  strRet = _getch();
  return atoi( strRet.c_str() );
}


         ///////////////////////////////////////////////////////////////
        // The Computer is Building the Inventory
       //       Arguments: None
      //           Return: None
     //  This function creates a sudo-inventory of randomly
    //   imagined vehicles and adds them to the inventory map.
   //    The model has been set to "Generic", as there are too
  //     many models for the 22 manufacturers to manipulate
 //      for the purposes of this application.
///////////////////////////////////////////////////////////////
void buildInventory( void )
{
  bool
    bFound     = false;
  char
    chVal[6];
  int
    rnd        = 0,
    indx       = 0,
    baseVin    = 100,
    count      = sizeof(MAKES)/sizeof(*MAKES);
  string
    strColors[] = { "Blue", "Red", "White", "Green", "Yellow", "Teal", "Gray", "Black" },
    strMake    = "",
    strModel   = "",
    strColor   = "",
    strYear    = "",
    strVin     = "",
    strCountry = "";
  iter_map_Inventory
    iter;

  for ( int i = 0; i < 20; i++ )
  {
    rnd        = rand()%( 22 );
    strMake    = MAKES[rnd];
    strCountry = getCountry( strMake );
    rnd        = rand()%( 8 );
    strColor   = strColors[rnd];
    rnd        = rand()%( 2 );
    _itoa_s( rnd, chVal, 6, 10 );
    strYear    = "20" + (string)chVal;
    rnd        = rand()%( 10 );
    _itoa_s( rnd, chVal, 6, 10 );
    strYear    = strYear + (string)chVal;
    rnd        = rand()%( 101 ) + 100;
    _itoa_s( rnd, chVal, 6, 10 );
    strVin     = "VIN-" + (string)chVal;

    bFound = false;

    for (iter = Inventory.begin(); iter != Inventory.end(); ++iter )
    {
      if ( iter->first == strVin )
      {
        i--;
        bFound = true;
        break;
      }
    }
    
    if ( !bFound )
    {
      CAutomobile
        vehicle( strMake, strCountry, strVin, "Generic", strColor, strYear );
      Inventory.insert( map_Pair( strVin, vehicle ) );
    }
  }
}


       ///////////////////////////////////////////////////////////////
      // User is Building the Inventory
     //       Arguments: None
    //           Return: None
   //  This function allows the user to specify the data for
  //   the vehicles and adds them to the inventory map. The
 //    vehicles are mapped VIN number to vehicle.
///////////////////////////////////////////////////////////////
void addVehicleToInventory( void )
{
  system( CLR );
  bool
    bFound   = false;
  int
    indx       = 0;
  string
    strVin     = "",
    strColor   = "",
    strYear    = "",
    strModel   = "",
    strMake    = "",
    strCountry = "";

  do
  {
    strVin = getVin();
    bFound = verifyVIN( strVin );
  } while ( bFound );

  strColor = getColor();
  strYear  = getYear();
  strMake  = getManufacturer();
  strModel = getModel();

  strCountry = getCountry( strMake );

  CAutomobile
    vehicle( strMake, strCountry, strVin, strModel, strColor, strYear );

  Inventory.insert( map_Pair( strVin, vehicle ) );
}


      ///////////////////////////////////////////////////////////////
     // User Removes a Vehicle the Inventory
    //       Arguments: string _vinNumber, unique vehicle identifier
   //           Return: None
  //  This function allows the user to specify the VIN number
 //   of the vehicle to be removed from the inventory map.
///////////////////////////////////////////////////////////////
void removeVehicleFromInventory( string _vinNumber)
{
  iter_map_Inventory
    itDel;

  itDel = Inventory.find( _vinNumber );

  if ( itDel != Inventory.end() )
  {
    Inventory.erase( itDel );
  }
}


      ///////////////////////////////////////////////////////////////
     // Complete Listing of the Vehicle Inventory
    //       Arguments: None
   //           Return: None
  //  This function allows the user to request a complete
 //   listing of the vehicles currently in the inventory map.
///////////////////////////////////////////////////////////////
void displayAllVehicles( void )
{
  iter_map_Inventory
    iter;

  system( CLR );

  for (iter = Inventory.begin(); iter != Inventory.end(); ++iter )
  {
    iter->second.print();
  }

  cout << "\n\n\n Press Any Key to Continue ... ";
  _getch();
}


      ///////////////////////////////////////////////////////////////
     // Listing of the Vehicle Inventory Filtered by Year
    //       Arguments: string _year, year of vehicles to display
   //           Return: None
  //  This function allows the user to request a listing of the
 //   vehicles currently in the inventory map Filtered by Year.
///////////////////////////////////////////////////////////////
void displayVehiclesByYear( string _year )
{
  iter_map_Inventory
    iter;

  system( CLR );

  for (iter = Inventory.begin(); iter != Inventory.end(); ++iter )
  {
    if ( iter->second.getYear() == _year )
    {
      iter->second.print();
    }
  }

  cout << "\n\n\n Press Any Key to Continue ... ";
  _getch();
}


       ///////////////////////////////////////////////////////////////
      // Listing of the Vehicle Inventory Filtered by Make
     //       Arguments: string _manufacturer, maker of the vehicle
    //           Return: None
   //  This function allows the user to request a listing of the
  //   vehicles currently in the inventory map Filtered by the
 //    make of the vehicles - the manufacturer.
///////////////////////////////////////////////////////////////
void displayVehiclesByMake( string _manufacturer )
{
  iter_map_Inventory
    iter;

  system( CLR );

  for (iter = Inventory.begin(); iter != Inventory.end(); ++iter )
  {
    if ( iter->second.getName() == _manufacturer )
    {
      iter->second.print();
    }
  }

  cout << "\n\n\n Press Any Key to Continue ... ";
  _getch();
}


      ///////////////////////////////////////////////////////////////
     // Retrieve the VIN Number
    //       Arguments: None
   //           Return: string strVin, unique identifier
  //  This function allows the user to input the VIN number
 //   for a vehicle as they add it to the inventory map.
///////////////////////////////////////////////////////////////
string getVin( void )
{
  string
    strVin = "";

  system( CLR );

  cout << "\n\n ! Press ENTER to Complete !\n" << endl;
  strVin = getField( " > Enter the VIN Number: VIN-", "999", "___" );
  strVin = "VIN-" + strVin;

  return strVin;
}


bool verifyVIN( string _vinNumber )
{
  bool
    bFound = false;
  iter_map_Inventory
    iter;

  for (iter = Inventory.begin(); iter != Inventory.end(); ++iter )
  {
    if ( iter != Inventory.end() )
    {
      if ( iter->first == _vinNumber )
      {
        cout << "\n\n ! That VIN Number is Already in Use !\n" << endl;
        clock_t tstart = clock();
        clock_t tend   = clock();

        do
        {
          tend = clock();
        } while ( (tend - tstart) / (double) CLOCKS_PER_SEC < 2.0 );
        bFound = true;
      }
    }
  }

  return bFound;
}


      ///////////////////////////////////////////////////////////////
     // Retrieve the Color Number
    //       Arguments: None
   //           Return: string strColor, color of the vehicle
  //  This function allows the user to input the color
 //   for a vehicle as they add it to the inventory map.
///////////////////////////////////////////////////////////////
string getColor( void )
{
  string
    strColor = "";

  system( CLR );
  cout << "\n\n ! Press ENTER to Complete !\n" << endl;
  strColor = getField( " > Enter the Color for This Vehicle: ", "AAAAAAAAAA", "__________" );

  return strColor;
}


      ///////////////////////////////////////////////////////////////
     // Retrieve the Vehicle Year
    //       Arguments: None
   //           Return: string strYear, year of the vehicle
  //  This function allows the user to input the year
 //   for a vehicle as they add it to the inventory map.
///////////////////////////////////////////////////////////////
string getYear( void )
{
  int
    nYear   = 99;
  string
    strYear = "";

  do
  {
    system( CLR );
    cout << "\n\n ! Model Year is in the Format 20## !\n" << endl;
    cout << " ! >>Acceptable Range is 00 to 19<< !\n\n" << endl;
    strYear = getField( " > Enter Model Year to Display: 20", "99", "__" );
    nYear   = atoi( strYear.c_str() );

    if ( nYear > 19 )
    {
      cout << "\n\n  Invalid year" << endl;
      clock_t tstart = clock();
      clock_t tend   = clock();

      do
      {
        tend = clock();
      } while ( (tend - tstart) / (double) CLOCKS_PER_SEC < 2.0 );
    }
  } while ( nYear > 19 );

  return "20" + strYear;
}


      ///////////////////////////////////////////////////////////////
     // Retrieve the Vehicle Model
    //       Arguments: None
   //           Return: string strModel, model of the vehicle
  //  This function allows the user to input the model
 //   for a vehicle as they add it to the inventory map.
///////////////////////////////////////////////////////////////
string getModel( void )
{
  string
    strModel = "";

  system( CLR );
  cout << "\n\n ! Press ENTER to Complete !\n" << endl;
  strModel = getField( " > Enter the Model for This Vehicle: ", "XXXXXXXXXXXXXXX", "_______________" );

  return strModel;
}


        ///////////////////////////////////////////////////////////////
       // Retrieve the Vehicle Manufacturer
      //       Arguments: None
     //           Return: string strMake, the manufacturer
    //  This function allows the user to input the manufacturer
   //   for a vehicle as they add it to the inventory map. It 
  //    also accepts a few alternate names and converts them 
 //     to the formal name expected by the application.
///////////////////////////////////////////////////////////////
string getManufacturer( void )
{
  int
    indx    = 0,
    count   = sizeof(MAKES) / sizeof(*MAKES);
  string
    strMake = "";

  do
  {
    system( CLR );
    cout << "\n\n ! Press ENTER to Complete !\n" << endl;
    strMake = getField( " > Enter the Manufacturer to Display: ", "AAAAAAAAAAAAAAA", "_______________" );

    if ( strMake == "Ram" ) strMake == "Dodge";
    if ( strMake == "GM" ) strMake == "General Motors";
    if ( strMake == "Chevy" ) strMake == "Cheverolet";
    if ( strMake == "Mercedes" ) strMake == "Mercedes Benz";

    for (indx = 0; indx < count; indx++)
    {
      if ( MAKES[indx] == strMake ) break;
    }

    if ( indx >= count )
    {
      cout << "\n\n  Invalid Manufacturer " << endl;
      clock_t tstart = clock();
      clock_t tend   = clock();

      do
      {
        tend = clock();
      } while ( (tend - tstart) / (double) CLOCKS_PER_SEC < 2.0 );
    }
  } while ( indx >= count );

  return strMake;
}


        ///////////////////////////////////////////////////////////////
       // Retrieve the Vehicle Manufacturer
      //       Arguments: _make, the manufacturer
     //           Return: string strCountry, the country of origin
    //  This function accepts the name of the manufacturer
   //   for a vehicle determins the country of origin by 
  //    looking up the maker in a string array and based on
 //     its location in the array calculates the country. 
///////////////////////////////////////////////////////////////
string getCountry( string _make )
{
  int
    indx  = 0,
    count = sizeof( MAKES )/sizeof( *MAKES );
  string
    strCountry = "";

  for ( indx = 0; indx < count; indx++ )
  {
    if ( MAKES[indx] == _make ) break;
  }

  if ( indx > 19 && indx < 22 )
    strCountry = "South Korea";
  else if ( indx > 16 && indx < 20 )
    strCountry = "Germany";
  else if ( indx > 10 && indx < 17 )
    strCountry = "Japan";
  if ( indx > -1 && indx < 11 )
    strCountry = "USA";

  return strCountry;
}


         ///////////////////////////////////////////////////////////////
        // Collect User Input According to an Input Mask
       //       Arguments: const char* chPrompt, request question
      //                   const char* chMask, Filter to limit input
     //                    const char* chMaskDesc, what is displayed 
    //                                             during input
   //           Return: string chHold, the data input by the user
  //  Display mask description while requesting user input and 
 //   replaces mask description characters with filtered input.
///////////////////////////////////////////////////////////////
string getField( const char* chPrompt, const char* chMask, const char* chMaskDesc )
{
  int
    ch,
    nWhich;
  char
    chHold[16] = "";
  bool
    bGoodInput = false;

  cout << chPrompt << chMaskDesc;
  flush( cout );

  for ( nWhich = 0; nWhich < (int)strlen( chMaskDesc ); nWhich++ )
  {
    cout << "\b";
  }

  flush( cout );
  nWhich = 0;

  do
  {
    ch = _getch();
    bGoodInput = false;

    if ( ch == '~' )
    {
      exit( 0 ); // Bailing out...
    }

    if ( ( chMaskDesc[nWhich] != '_' ) && ch != '\b' && ch != 13 )
    {
      chHold[nWhich] = chMaskDesc[nWhich];
      cout << chMaskDesc[nWhich];
      nWhich++;
      chHold[nWhich] = '\0';
    }

    if ( chMask[nWhich] == '9' && isdigit( ch ) )
    {
      bGoodInput = true;
    }
    else if ( chMask[nWhich] == 'A' && isalpha( ch ) )
    {
      bGoodInput = true;
    }
    else if ( chMask[nWhich] == 'X' && isprint( ch ) )
    {
      bGoodInput = true;
    }
    else if ( iscntrl( ch ) )
    {
      if ( ch == '\b' ) // Allow backspace for corrections
      {
        if ( nWhich > 0 )
        {
          if ( chMaskDesc[nWhich - 1] != '_' )
          {
            nWhich--;
            cout << "\b" << chMaskDesc[nWhich] << "\b";
            if ( nWhich > 0 )
            {
              nWhich--;
              cout << "\b" << chMaskDesc[nWhich] << "\b";
            }
          }
          else
          {
            nWhich--;
            cout << "\b" << chMaskDesc[nWhich] << "\b";
            flush( cout );
            chHold[nWhich] = '\0';
          }
          continue;
        }
        else
        {
          continue;
        }
      }
      nWhich = strlen( chMask );
    }

    if ( bGoodInput )
    {
      chHold[nWhich] = (char)ch;
      chHold[nWhich + 1] = '\0';
      cout << (char)ch;
      flush( cout );
      nWhich++;
    }
  } while ( nWhich < (int)strlen( chMask ) );

  return ( string( chHold ) );
}


     ///////////////////////////////////////////////////////////////
    // Draw a separator Line
   //       Arguments: None
  //           Return: None
 //  Draws a horizontal line for delimiting the main menu.
///////////////////////////////////////////////////////////////
void drawLine()
{
  cout << endl;
  for (int nLup = 0; nLup < 25; nLup++) cout << "==";
  cout << "\n" << endl;
}

