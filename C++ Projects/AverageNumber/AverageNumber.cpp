
  /////////////////////////////////////////
 // Average Number ///////////////////////
/////////////////////////////////////////
#include <conio.h>
#include <iostream>

using namespace std;
#pragma warning( disable:4786 )
#include <vector>

typedef vector<double> vec;
typedef vector<double>::iterator vecIT;

struct hilo
{
  double hi;
  double lo;
};


class AverageNumber
{
protected:
  vec dblCollection;

public:
  hilo stHiLo;

public:
  AverageNumber();
  AverageNumber( vec v ) { dblCollection = v; }
  AverageNumber( const AverageNumber & avn );

  AverageNumber& operator = ( AverageNumber avn );
  void addValue( double d );
  void clearVec( void );
  void hiLow( hilo & hl );
  double average( void );
};

AverageNumber::AverageNumber( const AverageNumber & avn )
{
  *this = avn;
}

AverageNumber& AverageNumber::operator = ( AverageNumber avn )
{
  swap( *this, avn );
  return *this;
}

void AverageNumber::addValue( double d )
{
  dblCollection.push_back( d );
}

void AverageNumber::clearVec()
{
    dblCollection.clear();
}

void AverageNumber::hiLow( hilo & hl )
{
  vecIT vIT = dblCollection.begin();

  hl.hi = (*vIT),
  hl.lo = (*vIT);

  for ( ; vIT != dblCollection.end(); vIT++ )
  {
    hl.hi = ( (*vIT) > hl.hi ) ? (*vIT) : hl.hi;
    hl.lo = ( (*vIT) < hl.lo ) ? (*vIT) : hl.lo;
  }
}

double AverageNumber::average()
{
  vecIT vIT = dblCollection.begin();
  double dbl = 0.0;

  for ( ; vIT != dblCollection.end(); vIT++ )
  {
    dbl += (*vIT);
  }

  return ( dbl/dblCollection.size() );
}


class ANDerived : public AverageNumber
{
public:
  ANDerived();

  double average();
};

double ANDerived::average()
{
  double dbl = AverageNumber::average();

  return ( ( dbl > 0.0 ) ? dbl : 0.0 );
}



int main()
{
  return 0;
}

