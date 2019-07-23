#-------------------------------------------------------------------------------------------------#
#----[ Version 1.0 ]-----===#[ Verification Report - by Victor Trucker ]#===-----[ 25-JUN-18 ]----#
#-------------------------------------------------------------------------------------------------#
__author__ = 'Victor Trucker'

import json, datetime
from sys import argv
from datetime import datetime


INCLUDE_JSON=False
SANITY_CHECK=False
VERIFY_JSON=False

USAGE = '''
╔════════[ USAGE: verificationReport.py ]═══════════╗
║                                                   ║
╠═════════════════[ COMMANDLINE: ]══════════════════╣
║                                                   ║
║     [python] verificationReport.py [/J /S /V]     ║
║                                                   ║
╠═════════════════[ DESCRIPTION: ]══════════════════╣
║                                                   ║
║  Arguments are optional and case independent. If  ║
║  all the arguments are ommitted just the summary  ║
║  gap and overlap information will be included in  ║
║  the report (no JSON Output or Sanity Checks).    ║
║                                                   ║
╠═══════════════════[ SWITCHES: ]═══════════════════╣
║                                                   ║
║    /J = Include JSON Response in Report Output    ║
║    /S = Sanity Checks :::::::: (for Debugging)    ║
║    /V = Verify JSON Input :::: (for Debugging)    ║
║                                                   ║
╚═══════════════════════════════════════════════════╝
'''
#-------------------------------------------------------------------------------------------------#
#------------------------------===::[ Command Line Arguments ]::===-------------------------------#
#-------------------------------------------------------------------------------------------------#
# set SANITY_CHECK and/or VERIFY_JSON to True for 'Sanity Checks' in output
# set INCLUDE_JSON to True to include the original JSON in the report output
for i in range(1,len(argv),1):
    if argv[ i ] in '/J/j-J-j':
        INCLUDE_JSON=True
    elif argv[ i ] in '/S/s-S-s':
        SANITY_CHECK=True
    elif argv[ i ] in '/V/v-V-v':
        VERIFY_JSON=True
    else:
        print (USAGE)
        raise SystemExit(0)

# for now this just reads in the JSON for processing
connection_file = open('validationReport.json', 'r')
data = json.load(connection_file)
connection_file.close()

# set separator and pad lengths
#-------------------------------------------------------------------------------------------------#
#----------------------------------===::[ Initialization ]::===-----------------------------------#
#-------------------------------------------------------------------------------------------------#
now = datetime.now()

separatorLen=102
separator='-' * separatorLen
einie=meinie=mynie=''

reportTitle='~==::[ GAP AND OVERLAP VALIDATION REPORT ]::==~'
reportFooter='~==::[ GAP AND OVERLAP VALIDATION REPORT COMPLETED SUCCESSFULLY ]::==~'
dateTime='~==::[ '+now.strftime("%m/%d/%Y ]::[ %H:%M:%S")+' ]::==~'

titleLen=len(reportTitle)
footerLen=len(reportFooter)
dateTimeLen=len(dateTime)

length1=int((separatorLen-titleLen)/2)
titlePad='-' * length1
length2=int((separatorLen-footerLen)/2)
footerPad='-' * length2
length3=int((separatorLen-dateTimeLen)/2)
dateTimePad='-' * length3

if length1<((separatorLen-titleLen)/2):
    einie='-'
if length2<((separatorLen-footerLen)/2):
    meinie='-'
if length3<((separatorLen-dateTimeLen)/2):
    mynie='-'

# dump JSON contents for verification sanity check
#-------------------------------------------------------------------------------------------------#
#--------------------------------===::[ Display Input JSON ]::===---------------------------------#
#-------------------------------------------------------------------------------------------------#
if VERIFY_JSON:
    print (' ')
    print (separator)
    print ('>>> Input JSON Verification: <<<')
    print (separator)
    print (json.dumps(data, sort_keys=False, indent=3))
    print (separator)
    print (separator)
    print (' ')

if SANITY_CHECK:
    print ('<<<>>> Sanity Check Point 1 <<<>>>')

print (' ')

print (separator)
print (dateTimePad + dateTime + dateTimePad + mynie)

print (separator)
print (titlePad + reportTitle + titlePad + einie)

#-------------------------------------------------------------------------------------------------#
#------------------------------------===::[ Start Work ]::===-------------------------------------#
#-------------------------------------------------------------------------------------------------#
try:
    if SANITY_CHECK:
        print ('<<<>>> Sanity Check Point 2 <<<>>>')
    else:
        print (separator)

# provide summary header
#-------------------------------------------------------------------------------------------------#
#------------------------------------===::[ entry point ]::===------------------------------------#
#-------------------------------------------------------------------------------------------------#
    print ('SUMMARY INFORMATION:')
    print ('  Channel Provider ID:  ', data['results']['channel_provider_id'])
    print ('  Error Return:         ', data['errors'])
    print ('  Warning Return:       ', data['warnings'])

    if SANITY_CHECK:
        print ('<<<>>> Sanity Check Point 3 <<<>>>')
    else:
        print(separator)

# itemize the gaps found (if any)
#-------------------------------------------------------------------------------------------------#
#--------------------------------------===::[ G A P S ]::===--------------------------------------#
#-------------------------------------------------------------------------------------------------#
    print ('GAPS:')
    for i in range(0,len(data['results']['gaps']),1):
        strBegTime = data['results']['gaps'][ i ][0]['begin_time']
        strEvent1  = data['results']['gaps'][ i ][0]['event_name']
        strEvent2  = data['results']['gaps'][ i ][1]['event_name']
        print ('  Gap Found at:     ', strBegTime, ' <between> ', strEvent1, ' <and> ', strEvent2)

    if len(data['results']['gaps']) < 1:
        print ('  >>> NO Gaps Found <<<')

    if SANITY_CHECK:
        print ('<<<>>> Sanity Check Point 4 <<<>>>')
    else:
        print(separator)

# itemize the overlaps found (if any)
#-------------------------------------------------------------------------------------------------#
#----------------------------------===::[ O V E R L A P S ]::===----------------------------------#
#-------------------------------------------------------------------------------------------------#
    print ('OVERLAPS:')
    for i in range(0,len(data['results']['overlaps']),1):
        strBegTime = data['results']['overlaps'][ i ][0]['begin_time']
        strEvent1  = data['results']['overlaps'][ i ][0]['event_name']
        strEvent2  = data['results']['overlaps'][ i ][1]['event_name']
        print ('  Overlap Found at: ', strBegTime, ' <between> ', strEvent1, ' <and> ', strEvent2)

    if len(data['results']['overlaps']) < 1:
        print ('  >>> NO Overlaps Found <<<')

    if SANITY_CHECK:
        print ('<<<>>> Sanity Check Point 5 <<<>>>')
    else:
        print(separator)

    if INCLUDE_JSON:
        print ('JSON Response:')
        print (separator)
        print (json.dumps(data, sort_keys=False, indent=3))
        print (separator)

    print (footerPad + reportFooter + footerPad + meinie)
    print (separator)

    raise SystemExit(0)

#-------------------------------------------------------------------------------------------------#
#-------------------------------===::[ An Exception Occurred ]::===-------------------------------#
#-------------------------------------------------------------------------------------------------#
except (ValueError, KeyError, TypeError):
    print ('>>> JSON format ERROR <<<')
    raise SystemExit(999)
