/************************************************************
*   Program: Project 10 - Part 1 - VTTTortoise.java         *
*    Author: Victor Trucker                                 *
*      Date: 05/01/2000                                     *
*   Purpose: This class generates a Tortoise critter to run *
*            in the historic race between the Tortoise and  *
*            the Hare using multi threading to manage the   *
*            racers...                                      *
************************************************************/


public class VTTTortoise extends VTTRacer
{
  public VTTTortoise( VTTRacerFrame frm, int nWinns )
  {
    super( frm );
    nType    = super.TORTOISE;
    strRacer = new String( " Tortoise " );
    nWins    = nWinns;
  } // end VTTTortoise( VTTRacerFrame )


  public void go()
  {
    super.go();
  } // end go()


  public void run()
  {
    do
    {
      makeMove();
    } while ( nWinner < 0 );
  } // end run()


  public void makeMove()
  {
    int
      nMove  = ( int )( ( Math.random() * 10 ) + 1 ),
      nValue = ( nMove <= 5 )             ?   3 :
               ( nMove > 5 && nMove < 9 ) ?   2 :  -5;

    runRace( nValue );
  } // end makeMove()

} // end class VTTTortoise

