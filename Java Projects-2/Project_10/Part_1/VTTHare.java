/************************************************************
*   Program: Project 10 - Part 1 - VTTHare.java             *
*    Author: Victor Trucker                                 *
*      Date: 05/01/2000                                     *
*   Purpose: This class generates a Hare critter to run in  *
*            the historic race between the Tortoise and the *
*            Hare using multi threading to manage the       *
*            racers...                                      *
************************************************************/


public class VTTHare extends VTTRacer
{
  public VTTHare( VTTRacerFrame frm, int nWinns )
  {
    super( frm );

    nType    = super.HARE;
    strRacer = new String( " Hare " );
    nWins    = nWinns;
  } // end VTTHare( VTTRacerFrame )


  public void go()
  {
    super.go();
  } // end go()


  public void run()
  {
    do
    {
      makeMove();
    } while ( super.nWinner < 0 );
  } // end run()


  public void makeMove()
  {
    int
      nMove  = ( int )( ( Math.random() * 10 ) + 1 ),
      nValue = ( nMove <= 2 )             ?   9 :
               ( nMove > 2 && nMove < 6 ) ?   1 :
               ( nMove == 6 )             ? -12 :
               ( nMove > 6 && nMove < 9 ) ?  -2 :   0;

    runRace( nValue );
  } // end makeMove()

} // end class VTTHare

