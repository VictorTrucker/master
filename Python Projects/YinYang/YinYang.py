#-----------------------------------------#
#---===#[ Y I N   A N D   Y A N G ]#===----#
#-------------------------------------------#
#-----------------------------------------------------------------------------------#
#-----===#[ Yin and Yang - Test ]---[ by Victor T Trucker ]---[ 13-MAR-19 ]#===------#
#-----===#[ Copyright (c) 2019, by Victor T Trucker, All Rights Reserved ]#===--------#
#--------------------------------------------------------------------------------------#
__author__    = 'Victor T Trucker'
__appName__   = 'Yin and Yang'
__version__   = '1.0.14'
__copyright__ = 'Copyright (c) 2019, by Victor T Trucker, All Rights Reserved'

import socket
from time import sleep
from winsound import Beep
from random import randint
from datetime import datetime
from pickle import dumps, loads
from msvcrt import kbhit, getch
from os import system, path, remove, rename, popen
from ctypes import c_short, c_uint, c_long, c_ulong, c_int, c_byte, c_bool
from ctypes import c_wchar, sizeof, windll, Structure, pointer, byref

from interface import OPPO_ITEM, OPPO_MORE, OPPO_WAIT, OPPO_MENU
from interface import AR_MAIN, AR_LOGO, AR_THANKS, BARS
from interface import SETS_MENU, OPPO_PROMPT, FONTS_MENU
from interface import makeHeader, padLine, flushInput

from interface import SAMPLE_INST as GAME_INST, INST1, INST2

#------------------------------------------------------------------------------------#
#----------------------------===#[ OS Considerations ]#===---------------------------#
#------------------------------------------------------------------------------------#
CLR   = 'cls'
VIEW  = 'mode con cols=%s lines=%s'
TITLE = 'title = %s'

#--==#[ Set Font to Lucida Console 14 Point ]#==--#
LF_FACESIZE       =  32
STD_OUTPUT_HANDLE = -11

class COORD(Structure):
    _fields_ = [("X", c_short),
                ("Y", c_short)]

class CONSOLE_FONT_INFOEX(Structure):
    _fields_ = [("cbSize",     c_ulong),
                ("nFont",      c_ulong),
                ("dwFontSize", COORD),
                ("FontFamily", c_uint),
                ("FontWeight", c_uint),
                ("FaceName",   c_wchar * LF_FACESIZE)]

font              = CONSOLE_FONT_INFOEX()
font.cbSize       = sizeof(CONSOLE_FONT_INFOEX)
font.dwFontSize.Y = 14
font.FaceName     = "Lucida Console"

handle = windll.kernel32.GetStdHandle(STD_OUTPUT_HANDLE)
windll.kernel32.SetCurrentConsoleFontEx(handle, c_long(False), pointer(font))

class CURSOR_INFO(Structure):
    _fields_ = [("size",    c_int),
                ("visible", c_byte)]

#------------------------------------------------------------------------------------#
#------------------------===#[ Runtime class Variables ]#===-------------------------#
#------------------------------------------------------------------------------------#
class GAME_INFO(Structure):
    _fields_ = [("inPlay",    c_bool),
                ("bail",      c_bool),
                ("dupServer", c_bool),
                ("data",      c_wchar * 128),
                ("users",     c_wchar * 64)]

    now        = None
    client     = None
    server     = None
    clientConn = None
    addr       = None

    def __init__(self, inPlay, dupServer):
        self.inPlay    = inPlay
        self.dupServer = dupServer
        self.bail      = False
        self.data      = ''
        self.users     = ''

#------------------------------------------------------------------------------------#
#---------------===#[ Runtime Game Specific Stats Storage Class ]#===----------------#
#------------------------------------------------------------------------------------#
class GAME_STATS(Structure):
    _fields_= [("game",      c_wchar * 3),
               ("which",     c_short),
               ("other",     c_short),
               ("team_name", c_wchar * 16),
               ("oppo_name", c_wchar * 16),
               ("conn_name", c_wchar * 18),
               ("hasMove",   c_bool),
               ("nextMove",  c_wchar * 16)]

    def __init__(self, game, which, other):
        self.game      = game
        self.which     = which
        self.other     = other
        self.conn_name = NOT_DEFINED
        self.team_name = NOT_DEFINED
        self.oppo_name = NOT_DEFINED
        self.hasMove   = True
        self.nextMove  = NOT_DEFINED

#------------------------------------------------------------------------------------#
#----------------------------===#[ Runtime Constants ]#===---------------------------#
#------------------------------------------------------------------------------------#
PNAME       = __appName__
VERSION     = __version__
BNAME       = makeHeader(PNAME)

LOGO_1      = '%s - Ver %s' % (PNAME, VERSION)
LOGO_HDR    = padLine(LOGO_1, 33)

DATA_FILES  = 'C:\\Users\\Public\\VTT\\Settings'
CONFIG_FILE = 'Config.cfg'
CONFIG_TEMP = 'Config.tmp'
ALLOWED     = '123456789∙'
NOT_DEFINED = 'NOT-DEFINED'
HOLD        = '%s'
DOT         = '∙'

#--==#[ Capture our Machine-Name, and IP Address ]#==--#
HOST_NAME = socket.gethostname().upper()
HOST_IP   = socket.gethostbyname(HOST_NAME)

#--==#[ The PORT needs to be unique to each Game ]#==--#
PORT      = 45450
BUFFER    = 1024

#--==#[ Colors for Interface ]#==--#
GREEN     = 'color 0A'
BLUE      = 'color 0B'
RED       = 'color 0C'
YELLOW    = 'color 0E'
WHITE     = 'color 0F'

#--==#[ Special Keys ]#==--#
FUNC      = 0
CTRL_C    = 3
BACKSP    = 8
TAB       = 9
ENTER     = 13
ESCAPE    = 27
HELP      = 59
EXTENDED  = 224

#--==#[ For Centering output ]#==--#
FULL_WIDTH   = 61
INST         = 75
WIDE         = str(FULL_WIDTH)
SHAVE        = FULL_WIDTH - 1
LOGO_WIDTH   = FULL_WIDTH - 31
OPPO_WIDTH   = FULL_WIDTH - 37

#--==#[ Primary window size ]#==--#
MAIN_VIEW   = VIEW % (WIDE,'30')
SETS_VIEW   = VIEW % (WIDE,'37')
INST_VIEW   = VIEW % (INST,'39')
OPPO_VIEW   = VIEW % (43, 31)

#--==#[ Create Menus, Settings and Logo ]#==--#
NEW_OPPO    = '\n'+padLine('╔═════════════════════════════╗', FULL_WIDTH).rstrip()
NEW_OPPO   += '\n'+padLine('║   Enter Your New Opponent   ║', FULL_WIDTH).rstrip()
NEW_OPPO   += '\n'+padLine('╚═════════════════════════════╝', FULL_WIDTH).rstrip()
NEW_OPPO   += '\n'+padLine('■                 ', FULL_WIDTH).rstrip()

LOST_OPPO   = '\n'+padLine('╔═══════════════════════════════════════════╗', FULL_WIDTH).rstrip()
LOST_OPPO  += '\n'+padLine('║  Your Opponent has Shutdown Their Client  ║', FULL_WIDTH).rstrip()
LOST_OPPO  += '\n'+padLine('╚═══════════════════════════════════════════╝', FULL_WIDTH).rstrip()

MAIN_MENU = '\n'
for item in AR_MAIN:
    if '%s' in item: item = '\n'+padLine(item % (padLine(BNAME, 41)), FULL_WIDTH).rstrip()
    else: item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    MAIN_MENU += item

LOGO = '\n'
for item in AR_LOGO:
    if '%s' in item: item = '\n'+padLine(item, LOGO_WIDTH).rstrip()
    else: item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    LOGO += item

THANKS = '\n\n'
thx    = padLine('Thanks for Playing %s' % PNAME, 39)
for item in AR_THANKS:
    if '%s' in item: item = '\n'+padLine(item % thx, FULL_WIDTH).rstrip()
    else: item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    THANKS += item

SETTINGS = '\n'
for item in SETS_MENU:
    if 'Opponent' in item: item = '\n'+padLine(item, FULL_WIDTH - 13).rstrip()
    else: item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    SETTINGS += item

FONT_MENU = '\n'
for item in FONTS_MENU:
    item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    FONT_MENU += item

OPPO_TEMP = '\n'
for item in OPPO_PROMPT:
    if '║%s║' in item: item = '\n'+padLine(item, OPPO_WIDTH).rstrip()
    elif '%s' == item: item = '\n'+item
    else: item = '\n'+padLine(item, FULL_WIDTH).rstrip()
    OPPO_TEMP += item

selection    = '\n'+padLine('Input Your Selection: ', FULL_WIDTH).rstrip() + ' '
MAIN_MENU   += selection
SETTINGS    += selection
FONT_MENU   += selection
LOGO         = LOGO % LOGO_HDR
OPPO_PROMPT  = OPPO_TEMP + NEW_OPPO
INSTRUCTIONS = INST1 + GAME_INST + (INST2 % VERSION)

WAIT          = padLine('■█■ Please Wait for Your Opponent ■█■', FULL_WIDTH)
USERS         = 'You Are: [%s] ■█■ They Are: [%s]'
INPUT_PROMPT  = padLine('■ Your Move Mr. %s ■', FULL_WIDTH - 4).rstrip()
INPUT_PROMPT += '\n\n'+padLine('■ Select ( 1 thru 9 ):  ', FULL_WIDTH).rstrip() + ' '

#--==#[ Runtime Variables ]#==--#
theGame   = GAME_INFO(True, False)
stats     = GAME_STATS('0', -1, -1)

#------------------------------------------------------------------------------------#
#-------vvv--------===#[ This Will be replaced with Real Data ]#===-------vvv--------#
#------------------------------------------------------------------------------------#
TOP  = '\n'+padLine('╔'+'═'*45+'╗', SHAVE)
MID  = '\n'+padLine('╠'+'═'*45+'╣', SHAVE)
MIB  = '\n'+padLine('║'+' '*45+'║', SHAVE)
BOT  = '\n'+padLine('╚'+'═'*45+'╝', SHAVE)

CONTINUE    = '\n'+padLine('║          Press Any Key to Continue          ║', SHAVE)

CAP         = TOP + MID + MIB
BASE        = MIB + CONTINUE + MIB + MID + BOT

VS_COMPUTER = CAP + '\n'+padLine('║      Player vs Computer, this Computer      ║', SHAVE) + BASE
VS_PLAYER_L = CAP + '\n'+padLine('║   Player vs Player, Both on this Computer   ║', SHAVE) + BASE
#------------------------------------------------------------------------------------#
#-------^^^--------===#[ This Will be replaced with Real Data ]#===-------^^^--------#
#------------------------------------------------------------------------------------#

#------------------------------------------------------------------------------------#
#-----------------------===#[ Set the Font to the Screen ]#===-----------------------#
#------------------------------------------------------------------------------------#
def setScreenFont(size):
    font.dwFontSize.Y = size

    handle = windll.kernel32.GetStdHandle(STD_OUTPUT_HANDLE)
    windll.kernel32.SetCurrentConsoleFontEx(handle, c_long(False), pointer(font))

#------------------------------------------------------------------------------------#
#--------------------===#[ Hide the Cursor During Wait Time ]#===--------------------#
#------------------------------------------------------------------------------------#
def hide_cursor():
    ci = CURSOR_INFO()
    handle = windll.kernel32.GetStdHandle(STD_OUTPUT_HANDLE)
    windll.kernel32.GetConsoleCursorInfo(handle, byref(ci))
    ci.visible = False
    windll.kernel32.SetConsoleCursorInfo(handle, byref(ci))

#------------------------------------------------------------------------------------#
#--------------------===#[ Show the Cursor During Play Time ]#===--------------------#
#------------------------------------------------------------------------------------#
def show_cursor():
    ci = CURSOR_INFO()
    handle = windll.kernel32.GetStdHandle(STD_OUTPUT_HANDLE)
    windll.kernel32.GetConsoleCursorInfo(handle, byref(ci))
    ci.visible = True
    windll.kernel32.SetConsoleCursorInfo(handle, byref(ci))

#------------------------------------------------------------------------------------#
#---------------------------===#[ Play Victory Tune ]#===----------------------------#
#------------------------------------------------------------------------------------#
def playWin():
    Beep(494, 275)
    Beep(388, 170)
    Beep(388, 200)
    Beep(688, 1500)

#------------------------------------------------------------------------------------#
#----------------------------===#[ Play Defeat Tune ]#===----------------------------#
#------------------------------------------------------------------------------------#
def playLose():
    Beep(699, 200)
    Beep(699, 200)
    Beep(699, 200)
    Beep(588, 1400)

#------------------------------------------------------------------------------------#
#------------===#[ Display Game Instructions and Playtime Messages ]#===-------------#
#------------------------------------------------------------------------------------#
def displayInstructions(message, instView, mainView):
    system(CLR)
    system(instView)
    flushInput()
    hide_cursor()
    system(YELLOW)
    print(message)
    getch()
    system(BLUE)
    system(mainView)
    show_cursor()

#------------------------------------------------------------------------------------#
#----------===#[ Change the Font Size and Save it to the Config file ]#===-----------#
#------------------------------------------------------------------------------------#
def setFontSize():
    size     = 0
    hold     = str(font.dwFontSize.Y)
    fileName = '%s\\%s' % (DATA_FILES, CONFIG_FILE)
    tempName = '%s\\%s' % (DATA_FILES, CONFIG_TEMP)

    show_cursor()
    if not path.isdir(DATA_FILES): system('mkdir %s' % DATA_FILES)

    while size == 0:
        system(CLR)
        print(FONT_MENU % (hold), end = "", flush = True)
        temp = ord(getch())
        ch   = chr(temp).upper()

        if temp == ENTER or temp == ESCAPE or temp == CTRL_C:
            hide_cursor()
            return font.dwFontSize.Y
        elif ch in '1234567':
            size = int(ch) + 11

    hide_cursor()

    try:
        found = False

        with open(tempName, "w") as tempFile:
            with open(fileName, "r") as config:
                for item in config:
                    if 'FontSize' in item:
                        item = ('FontSize=%d\n' % size)
                        found = True

                    tempFile.write(item)

                if not found:
                    item = ('FontSize=%d\n' % size)
                    tempFile.write(item)
                    found = False

        config.close()
        tempFile.close()
        remove(fileName)
        rename(tempName, fileName)
    except:
        pass

    setScreenFont(size)
    return size

#------------------------------------------------------------------------------------#
#-------------------------===#[ Retrieve the Font Size ]#===-------------------------#
#------------------------------------------------------------------------------------#
def getFontSize():
    system(CLR)
    size     = 14
    fileName = '%s\\%s' % (DATA_FILES, CONFIG_FILE)

    if not path.isdir(DATA_FILES): system('mkdir %s' % DATA_FILES)

    #--==#[ If the Config file does not exist ]#==--#
    if not path.isfile(fileName):
        with open(fileName, "w") as config:
            config.write('FontSize=14\n')
            config.write('Opponent=NOT-DEFINED\n')
        return size

    try:
        with open(fileName, "r") as config:
            for item in config:
                item.strip('\n')
                if 'FontSize' in item:
                    n    = item.find('=') + 1
                    size = int(item[n:].strip('\n'))
                    break
    except:
        pass

    setScreenFont(size)
    return size

#------------------------------------------------------------------------------------#
#------------------===#[ Obtain a List of Potential Opponents ]#===------------------#
#------------------------------------------------------------------------------------#
def getOpponentList():
    content   = ''
    results   = []
    opponents = []

    print('\n\n%s' % OPPO_WAIT % padLine('Fetching Opponent List, Please Wait', 35))

    #--==#[ Create a List of Computers ]#==--#
    content = popen('arp -a').read()
    results = content.split('\n')

    for item in results:
        item = item.strip()
        if item.find('dynamic') > -1 or item.find('static') > -1:
            try:
                IP   = item[:item.find(' ')]
                NAME = socket.gethostbyaddr(IP)[0].upper()
                if NAME != HOST_NAME and NAME.find('MCAST.NET') < 0: opponents.append(NAME)
            except:
                continue

    #--==#[ If NO Computers are found ]#==--#
    if len(opponents) < 1: opponents.append(NOT_DEFINED)

    #--==#[ Sort the Opponents ]#==--#
    opponents.sort()

    return opponents

#------------------------------------------------------------------------------------#
#---------===#[ Allow the User to Select their Opponent from our List ]#===----------#
#------------------------------------------------------------------------------------#
def selectOpponent():
    cnt = 0

    system(CLR)
    hide_cursor()

    opponents = getOpponentList()

    while 1:
        n = 0
        oppos = more = ch = ''
        system(CLR)
        flushInput()

        #--==#[ Build a Menu of Opponents ]#==--#
        while n < 9:
            sp = ' ' * (15 - len(opponents[n + (cnt * 9)]))
            oppos += OPPO_ITEM % (n + 1, '%s%s' % (opponents[n + (cnt * 9)].upper(), sp))
            n += 1
            if n + (cnt * 9) > len(opponents) - 1: break

        #--==#[ Access to More than 9 Opponents ]#==--#
        if len(opponents) > 9: more = OPPO_MORE

        print(OPPO_MENU % (oppos, more), end = " ", flush = True)

        while len(ch) < 1:
            temp = ord(getch())
            ch   = chr(temp).upper()

            #--==#[ Bail-Out ]#==--#
            if temp == ESCAPE or temp == CTRL_C: return DOT

            if 'M' == ch and len(opponents) > 9:
                cnt += 1
                if cnt * 9 > len(opponents): cnt = 0
                break

            if temp == 224:
                keycode = ord(getch())
                if keycode == 72:   # up-arrow
                    if cnt > 0: cnt -= 1
                elif keycode == 80: # dn-arrow
                    if (cnt + 1) * 9 < len(opponents): cnt += 1
                break

            if ch.isdigit():
                index = int(ch) + (cnt * 9)
                index -= 1
                if (index) not in range(len(opponents)): ch = ''
                else: return opponents[index].upper()

#------------------------------------------------------------------------------------#
#-----------===#[ Change the Opponent and Save it to the Config file ]#===-----------#
#------------------------------------------------------------------------------------#
def setOpponent():
    temp     = -1
    name     = ''
    hold     = getOpponent()
    fileName = '%s\\%s' % (DATA_FILES, CONFIG_FILE)
    tempName = '%s\\%s' % (DATA_FILES, CONFIG_TEMP)
    prompt   = OPPO_PROMPT % padLine(hold, 39)

    system(CLR)
    show_cursor()

    print('%s' % (prompt), end = " ", flush = True)

    while temp != ENTER:
        flushInput()
        temp = ord(getch())

        if temp == ESCAPE or temp == CTRL_C:
            name = ''
            return hold
        elif temp == BACKSP:
            system(CLR)
            if len(name) > 0: name = name[:len(name) - 1]
            print('%s %s' % (prompt, name), end = "", flush = True)
        elif temp == EXTENDED or temp == TAB:
            ch = ''
        else:
            ch = chr(temp).upper()
            print(('%s' % ch), end = "", flush = True)
            name += ch

    if len(name.strip()) < 1:
        system(OPPO_VIEW)
        name = selectOpponent()
        system(SETS_VIEW)

    if name == DOT: return hold

    hide_cursor()

    try:
        found = False

        with open(tempName, "w") as tempFile:
            with open(fileName, "r") as config:
                for item in config:
                    if 'Opponent' in item:
                        item = ('Opponent=%s\n' % name)
                        found = True

                    tempFile.write(item)

                if not found:
                    item = ('Opponent=%s\n' % name)
                    tempFile.write(item)
                    found = False

        config.close()
        tempFile.close()
        remove(fileName)
        rename(tempName, fileName)
    except:
        pass

    return name

#------------------------------------------------------------------------------------#
#---------------===#[ Retrieve the Opponent from the Config file ]#===---------------#
#------------------------------------------------------------------------------------#
def getOpponent():
    oppo     = NOT_DEFINED
    fileName = '%s\\%s' % (DATA_FILES, CONFIG_FILE)

    if not path.isdir(DATA_FILES): system('mkdir %s' % DATA_FILES)

    #--==#[ If the Config file does not exist ]#==--#
    if not path.isfile(fileName):
        with open(fileName, "w") as config:
            config.write('FontSize=14\n')
            config.write('Opponent=NOT-DEFINED\n')
        return oppo

    try:
        with open(fileName, "r") as config:
            for item in config:
                item.strip('\n')
                if 'Opponent' in item:
                    n    = item.find('=') + 1
                    oppo = item[n:].strip('\n')
                    break
    except:
        pass

    return oppo

#------------------------------------------------------------------------------------#
#--------------------===#[ Set Options for the Application ]#===---------------------#
#------------------------------------------------------------------------------------#
def settings():
    while 1:
        #--==#[ Set View ]#==--#
        system(GREEN)
        system(SETS_VIEW)
        show_cursor()

        #--==#[ Clear the Screen ]#==--#
        system(CLR)
        #--==#[ Display Settings Menu ]#==--#
        print(SETTINGS % (padLine('%s' % getOpponent(), 15), ('%d' % font.dwFontSize.Y)), end = "", flush = True)
        temp = ord(getch())
        selection = chr(temp)

        if temp == ESCAPE or temp == CTRL_C: break

        if selection in '1': setOpponent()
        elif selection in '2': setFontSize()

    show_cursor()
    system(MAIN_VIEW)

#-----------------------------------------------------------------------------------------------------------------#
#-----------------------------------------------------------------------------------------------------------------#
#-----------------------------------------------------------------------------------------------------------------#

#--------------------------------------------------------------------------------#
#---------------------------------------------------------------------------------#
#--------------===#[ Play the Game Modes Local to this Machine ]#===---------------#
#-----------------------------------------------------------------------------------#
#------------------------------------------------------------------------------------#
def localPlay():
    system(CLR)
    print(LOGO)

    if stats.game == '1':
        print('\n\n%s' % (VS_COMPUTER))
        getch()
    elif stats.game == '2':
        print('\n\n%s' % (VS_PLAYER_L))
        getch()

#-----------------------------------------------------------------------------------------------------------------#
#-----------------------------------------------------------------------------------------------------------------#
#-----------------------------------------------------------------------------------------------------------------#

#---------------------------------------------------------------------------------#
#------------===#[ Play the Game Network Mode from this Machine ]#===--------------#
#-----------------------------------------------------------------------------------#

#------------------------------------------------------------------------------------#
#--------------------------===#[ Setup the Client Side ]#===-------------------------#
#------------------------------------------------------------------------------------#
def prepClient():
    theGame.now = datetime.now().strftime("%H:%M:%S.%f")

    system(CLR)
    hide_cursor()
    print('\n%s' % (WAIT))
    count = 0

    while 1:
        try:
            #--==#[ Set up our socket and Connect to the Server ]#==--#
            theGame.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            theGame.client.connect((stats.conn_name, PORT))
            break
        except:
            sleep(timeOuts[count])
            count += 1
            if count >= len(timeOuts):
                theGame.client = None
                print(padLine('■█■ CLIENT FAILURE: No Opponent Available ■█■', FULL_WIDTH))
                sleep(4)
                return

    #--==#[ Accept our Opponents Connection ]#==--#
    theGame.clientConn, theGame.addr = theGame.server.accept()

    #--==#[ Display the Login of my Opponent to Me ]#==--#
    system(CLR)
    print('%s\n%s' % (LOGO, theGame.users))

#------------------------------------------------------------------------------------#
#--------------------------===#[ Setup the Server Side ]#===-------------------------#
#------------------------------------------------------------------------------------#
def prepServer():
    system(CLR)
    hide_cursor()

    try:
        #--==#[ Create Our Server ]#==--#
        #--==#[ Exclusive Address Use, ensures only One Server ]#==--#
        theGame.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        theGame.server.setsockopt(socket.SOL_SOCKET, socket.SO_EXCLUSIVEADDRUSE, 1)
    except socket.error:
        system(RED)
        system(CLR)
        print('%s\n\n%s' % (LOGO, padLine('■█■ SERVER FAILURE: The Socket is already in use ■█■', FULL_WIDTH)))
        theGame.server.close()
        theGame.inPlay = False
        theGame.dupServer = True
        sleep(4)
        return

    try:
        #--==#[ Bind Our Server to HOST_IP and PORT ]#==--#
        #--==#[ This will fail if the Server is already running ]#==--#
        theGame.server.bind((HOST_IP, PORT))
        theGame.server.listen(1)
    except socket.error:
        system(RED)
        system(CLR)
        print('%s\n\n%s' % (LOGO, padLine('■█■ SERVER FAILURE: The Server is already started ■█■', FULL_WIDTH)))
        theGame.server.close()
        theGame.inPlay = False
        theGame.dupServer = True
        sleep(4)
        return

    print('\n%s' % (padLine('■█■ Press Any Key to connect to your Opponent ■█■', FULL_WIDTH)))
    getch()

    prepClient()

#------------------------------------------------------------------------------------#
#----------------------===#[ Get Data to send to Opponent ]#===----------------------#
#------------------------------------------------------------------------------------#
def getData():
    flushInput()

    if stats.nextMove == stats.team_name:
        print(padLine(INPUT_PROMPT % (stats.team_name), FULL_WIDTH).rstrip(), end = " ", flush = True)
        val = '@'

        while val not in ALLOWED:
            show_cursor()
            temp = ord(getch())
            val  = chr(temp)

            if temp == FUNC:
                keycode = ord(getch())
                if keycode == HELP:
                    displayInstructions(INSTRUCTIONS, INST_VIEW, MAIN_VIEW)
                    print('%s\n%s\n\n' % (LOGO, theGame.users))
                    print(padLine(INPUT_PROMPT % (stats.team_name), FULL_WIDTH).rstrip(), end = " ", flush = True)
                    val = '@'

            if temp == ESCAPE or temp == CTRL_C: val = DOT

        hide_cursor()
        if DOT in val: info = [DOT, DOT, DOT]
        else: info = [stats.team_name, val]
        tell = dumps(info)

        if tell:
            try:
                #--==#[ client Sends data to Opponent ]#==--#
                theGame.client.send(tell)
                stats.nextMove = ' ' * 16
                stats.nextMove = stats.oppo_name
                stats.nextMove = stats.nextMove.rstrip()

                if DOT in val:
                    theGame.bail = True # game terminated
                    return
            except:
                #--==#[ The link is broken ]#==--#
                print(padLine('>>> DATA SEND ERROR <<<', FULL_WIDTH))
                sleep(3)
                return

    system(CLR)
    hide_cursor()
    print('%s\n%s\n\n%s' % (LOGO, theGame.users, WAIT))

#------------------------------------------------------------------------------------#
#--------------------===#[ Prime the Communication Process ]#===---------------------#
#------------------------------------------------------------------------------------#
def initComm():
    info = [theGame.now]
    tell = dumps(info)

    if tell:
        try:
            theGame.client.send(tell)
            theGame.inPlay = True
        except:
            #--==#[ The link is broken ]#==--#
            theGame.client.close()
            print('\n%s' % padLine('>>> FAILED TO SEND COMMUNICATION [%s] <<<' % (theGame.now), FULL_WIDTH))
            theGame.inPlay = False

    return theGame.inPlay

#------------------------------------------------------------------------------------#
#------------------===#[ The Main Driver for Communications ]#===--------------------#
#------------------------------------------------------------------------------------#
def networkPlay():
    #--==#[ Prep the Network Connection ]#==--#
    prepServer()
    #--==#[ Did We Succeed ]#==--#
    if not theGame.inPlay or theGame.dupServer or theGame.server == None or theGame.client == None: return

    system(GREEN)
    if not initComm(): return

    while theGame.inPlay:
        try:
            flushInput()
            #--==#[ clientConn Receives data from Opponent ]#==--#
            data = theGame.clientConn.recv(BUFFER)

            if data:
                system(CLR)
                shot = loads(data)

                if len(shot) == 1: # time stamp
                    #--==#[ Compare Time-Stamps, see Who is First ]#==--#
                    if theGame.now < shot[0]:
                        stats.team_name = 'First'
                        stats.oppo_name = 'Second'
                        stats.which     = 0
                        stats.other     = 1
                        stats.nextMove  = stats.team_name
                    else:
                        stats.team_name = 'Second'
                        stats.oppo_name = 'First'
                        stats.which     = 1
                        stats.other     = 0
                        stats.nextMove  = stats.oppo_name

                    theGame.users = padLine(USERS % (stats.team_name, stats.oppo_name), FULL_WIDTH)
                    print('%s\n%s\n' % (LOGO, theGame.users))

                elif len(shot) == 2: # from opponent
                    stats.nextMove = ' ' * 16
                    stats.nextMove = stats.team_name
                    stats.nextMove = stats.nextMove.rstrip()

                    #--==#[ We Will be Processing the Message, ]#==--#
                    #--==#[ rather than just displaying it here ]#==--#
                    rec = padLine('Received Data:', FULL_WIDTH)
                    dat = padLine(makeHeader('From: Mr. %s :: Data: [%s]' % (shot[0], shot[1])), FULL_WIDTH)
                    print('%s\n%s\n\n%s\n\n%s\n' % (LOGO, theGame.users, rec, dat))

                elif len(shot) == 3: # game terminated
                    if DOT in shot: raise Exception()

        except:
            system(CLR)
            hide_cursor()
            print('\n\n\n\n\n%s' % LOST_OPPO)
            sleep(3)
            return

        if theGame.inPlay:
            getData()

            if theGame.bail:
                theGame.bail = False
                return

#------------------------------------------------------------------------------------#
#-------------------------===#[ Display the Main Menu ]#===--------------------------#
#------------------------------------------------------------------------------------#
def main():
    selection = '@'
    getFontSize()

    while theGame.inPlay and selection not in '123':
        system(TITLE % PNAME)

        #--==#[ Clear the Screen ]#==--#
        system(CLR)

        #--==#[ Set View ]#==--#
        system(GREEN)
        system(MAIN_VIEW)
        show_cursor()

        print(MAIN_MENU, end = "", flush = True)
        temp = ord(getch())
        selection = chr(temp).upper()

        if temp == FUNC:
            keycode = ord(getch())
            if keycode == HELP:
                displayInstructions(INSTRUCTIONS, INST_VIEW, MAIN_VIEW)
            selection = '@'

        if temp == ESCAPE or temp == CTRL_C: theGame.inPlay = False

        if theGame.inPlay and selection in 'S': settings()

        if theGame.inPlay and selection in '123':
            hide_cursor()
            system(CLR)
            stats.game = selection

            fileName = '%s\\%s' % (DATA_FILES, CONFIG_FILE)

            #--==#[ Check if the Config file exists ]#==--#
            if not path.isfile(fileName):
                stats.conn_name = setOpponent()
                setFontSize()
            else:
                stats.conn_name = getOpponent()
                if stats.conn_name == NOT_DEFINED: stats.conn_name = setOpponent()
                getFontSize()

            # launchGame()
            system(CLR)

            if stats.game in '12':
                localPlay() # player vs computer, or player, on one computer
            else:
                networkPlay() # player vs player, over network

            selection = '@'

#-------------------------#
#--==#[ Entry Point ]#==---#
#---------------------------#
if __name__ == "__main__":
      main()

#------------------------------------------------------------------------------------#
#----------------===#[ Clean up and Say Bye-Bye - Yin and Yang ]#===-----------------#
#------------------------------------------------------------------------------------#
system(CLR)
system(RED)
hide_cursor()
print(LOGO + THANKS)
sleep(3)

#------------------------------------------------------------------------------------#
#--------------------------===#[ END of Yin and Yang ]#===---------------------------#
#------------------------------------------------------------------------------------#
