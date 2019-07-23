#----------------------------------------------------------------------------------#
#----[ Version 1.3 ]----===#[ Dice <-> by Victor Trucker ]#===----[ 25-JUN-18 ]----#
#------===#[ Copyright (c) 2018, by Victor Trucker, All Rights Reserved ]#===------#
#----------------------------------------------------------------------------------#
__author__    = 'Victor Trucker'
__version__   = "1.3"
__copyright__ = "Copyright (c) 2018, by Victor Trucker, All Rights Reserved"

from os import system
from time import sleep
from random import randint

#----------------------------------------------------------------------------------#
#----------------------------===#[ Runtime Values ]#===----------------------------#
#----------------------------------------------------------------------------------#
SHOW_DICE  = True
EMPTY_DICE = 0
SNAKE_EYES = 2
DEFAULT_7  = 7
DEFAULT_11 = 11
markD      = EMPTY_DICE

#----------------------------------------------------------------------------------#
#-----------------------------===#[ Runtime Text ]#===-----------------------------#
#----------------------------------------------------------------------------------#
HDR_BAR = ' ■──────────────────────────────────────────■\n'
HDR_TTL = ' │          ---===■[ DICE ]■===---          │\n'
HDR_QT  = ' │ ---===■[ Enter ANY Char to Quit ]■===--- │\n'
THANKS  = '\n <<=>> Thank you for playing Dice <<=>>\n      <<=>> Have a nice day <<=>>'
PROMPT  = ' >>> Press [Enter] to Roll the Dice ... '
PLAY1   = '\n >>> Starting a New Roller ...\n'
DIE1    = ' >>> DIE #1: [ %s ]\n'
DIE2    = ' >>> DIE #2: [ %s ]\n'
ROLL    = ' >>>>> Roll: [ %s ] <<<<<'
MARK    = ' >>>>> Mark: [ %s ] <<<<<\n'
WINS1   = '- HOORAY, You WIN -'
LOSE1   = '- SORRY, You LOSE -'
SEVEN   = '- Seven Come Eleven -'
MAKER   = '- Makers Mark -'
HARD1   = '- The Hard Way -'
SNAKE   = '- Snake Eyes -'
CRAPS   = '- Craps -'
HEADER  = HDR_BAR + HDR_TTL + HDR_BAR + HDR_QT + HDR_BAR

#----------------------------------------------------------------------------------#
#-----------------------------===#[ the Die data ]#===-----------------------------#
#----------------------------------------------------------------------------------#
HBAR  = '   ■───────■\n'
TOP1  = '   │ O     │\n'
MID1  = '   │   O   │\n'
BOT1  = '   │     O │\n'
TWO2  = '   │ O   O │\n'
NONE  = '   │       │\n'

#----------------------------------------------------------------------------------#
#--------------------===#[ the Array of Dice for Display ]#===---------------------#
#----------------------------------------------------------------------------------#
DICE = ['*E*M*P*T*Y*',
        '\n%s%s%s%s%s' % (HBAR, NONE, MID1, NONE, HBAR),
        '\n%s%s%s%s%s' % (HBAR, TOP1, NONE, BOT1, HBAR),
        '\n%s%s%s%s%s' % (HBAR, TOP1, MID1, BOT1, HBAR),
        '\n%s%s%s%s%s' % (HBAR, TWO2, NONE, TWO2, HBAR),
        '\n%s%s%s%s%s' % (HBAR, TWO2, MID1, TWO2, HBAR),
        '\n%s%s%s%s%s' % (HBAR, TWO2, TWO2, TWO2, HBAR)]

#----------------------------------------------------------------------------------#
#----------------------------===#[ the Dice class ]#===----------------------------#
#----------------------------------------------------------------------------------#
class Dice():
    value = 0

    def roll(self):
        self.value = randint(1, 6)

#----------------------------------------------------------------------------------#
#-----------------===#[ Clear the Screen and Start the Play ]#===------------------#
#----------------------------------------------------------------------------------#
system('cls')
print (HEADER)

#----------------------------------------------------------------------------------#
#---------------===#[ This is the Main Driver for this Script ]#===----------------#
#----------------------------------------------------------------------------------#
while 1:
    inStr = input(PROMPT).strip()

    system('cls')

    if inStr == '':
        Dice1 = Dice()
        Dice2 = Dice()
        gameOver = False
        roll = EMPTY_DICE

    #---===#[ roll the Dice ]#===---#
        Dice1.roll()
        Dice2.roll()

        print (HEADER)

    #---===#[ do we draw Dice, or just show numbers ]#===---#
        if SHOW_DICE:
            print((DIE1 % (Dice1.value)) + DICE[Dice1.value])
            print((DIE2 % (Dice2.value)) + DICE[Dice2.value])
        else:
            print(DIE1 % (Dice1.value))
            print(DIE2 % (Dice2.value))
    #---===#[ Add up the die values and display the results ]#===---#    
        roll = Dice1.value + Dice2.value
        print(ROLL % (roll))
    #----===#[ Determine roll status - win, lose, continue ]#===----#
        if EMPTY_DICE != markD:
            print(MARK % (markD))
        if SNAKE_EYES == roll:
            gameOver = True
            print(LOSE1 + SNAKE)
        elif EMPTY_DICE == markD:
            if roll == DEFAULT_7 or roll == DEFAULT_11:
                gameOver = True
                print(WINS1 + SEVEN)
            else:
                markD = roll
                print(MARK % (markD))
        elif DEFAULT_7 == roll or DEFAULT_11 == roll:
            gameOver = True
            print(LOSE1 + CRAPS)
        elif roll == markD:
            gameOver = True
            if Dice1.value == Dice2.value:
                print(WINS1 + MAKER + HARD1)
            else:
                print(WINS1 + MAKER)

    #---===#[ reset and start over ]#===---#
        if gameOver:
            markD = EMPTY_DICE
            gameOver = False
            print(PLAY1)
    else:
        break

#----------------------------------------------------------------------------------#
#-----------------------===#[ Clean up and say Bye-Bye ]#===-----------------------#
#----------------------------------------------------------------------------------#
system('cls')
print(THANKS)
sleep(3)
