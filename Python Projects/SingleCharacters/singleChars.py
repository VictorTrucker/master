#---------------------------------------------------#
#---===#[ S I N G L E   C H A R A C T E R S ]#===----#
#-----------------------------------------------------#
#-----------------------------------------------------------------------------------#
#----===#[ Single Characters ]-----[ by Victor T Trucker ]------[ 05-JUN-19 ]#===----#
#------===#[ Total Time Spent Developing This mini-Application: 30 minutes ]#===------#
#--------------------------------------------------------------------------------------#
__author__  = 'Victor T Trucker'
__appName__ = 'Single Characters'
__version__ = '1.0.00'

#-----------------------------------------------------------------------------#
#----------===#[ cumulative, non-destructive, revealing process ]#===----------#
#-------------------------------------------------------------------------------#
def main(cstr):
    chars = {}

    for ch in cstr.lower():
        try:
            if chars[ch] > 0:
                chars[ch] += 1
        except:
            chars[ch] = 1

    for item in chars.keys():
        print('\n>>> %s - number of occurrences: %d' % (item, chars[item]))

    print('\n>>> Singular Letters in %s: ' % (cstr), end = '', flush = True)

    for item in chars.keys():
        if chars[item] == 1:
            print(item, end = ' ', flush = True)

    print()

#----------------------------------------------------------------------------------#
#------------------------------===#[ Entry Point ]#===------------------------------#
#------------------------------------------------------------------------------------#
if __name__ == "__main__":
      main('BooKkeEper')


# #-----------------------------------------------------------------------------#
# #--------===#[ cumulative, non-destructive, non-revealing process ]#===--------#
# #-------------------------------------------------------------------------------#
# def main(cstr):
#     chars = {}

#     for ch in cstr.lower():
#         try:
#             if chars[ch] > 0:
#                 chars[ch] += 1
#         except:
#             chars[ch] = 1

#     print('\n>>> Singular Letters in %s: ' % (cstr), end = '', flush = True)

#     for item in chars.keys():
#         if chars[item] == 1:
#             print(item, end = ' ', flush = True)

#     print()

# #----------------------------------------------------------------------------------#
# #------------------------------===#[ Entry Point ]#===------------------------------#
# #------------------------------------------------------------------------------------#
# if __name__ == "__main__":
#       main('BooKkeEper')


# #-----------------------------------------------------------------------------#
# #-------------===#[ non-cumulative, non-destructive process ]#===--------------#
# #-------------------------------------------------------------------------------#
# def main(cstr):
#     chars = {}

#     for ch in cstr.lower():
#         try:
#             if chars[ch] == 1:
#                 chars[ch] = 2
#         except:
#             chars[ch] = 1

#     print('\n>>> Singular Letters in %s: ' % (cstr), end = '', flush = True)

#     for item in chars.keys():
#         if chars[item] == 1:
#             print(item, end = ' ', flush = True)

#     print()

# #----------------------------------------------------------------------------------#
# #------------------------------===#[ Entry Point ]#===------------------------------#
# #------------------------------------------------------------------------------------#
# if __name__ == "__main__":
#       main('BooKkeEper')


# #-----------------------------------------------------------------------------#
# #---------------===#[ non-cumulative, destructive process ]#===----------------#
# #-------------------------------------------------------------------------------#
# def main(cstr):
#     chars = {}

#     for ch in cstr.lower():
#         try:
#             if chars[ch] == 1:
#                 chars[ch] = 2
#         except:
#             chars[ch] = 1

#     for ch in cstr:
#         try:
#             if chars[ch] == 2:
#                 chars.pop(ch)
#         except:
#             pass

#     print('\n>>> Singular Letters in %s: ' % (cstr), end = '', flush = True)

#     for item in chars.keys():
#         print(item, end = ' ', flush = True)

#     print()

# #----------------------------------------------------------------------------------#
# #------------------------------===#[ Entry Point ]#===------------------------------#
# #------------------------------------------------------------------------------------#
# if __name__ == "__main__":
#       main('BooKkeEper')
