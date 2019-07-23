#-----------------------------------------#
#---===#[ C O L O R   A S S I G N ]#===----#
#-------------------------------------------#
#-----------------------------------------------------------------------------------#
#-----===#[ Color Assignment ]-----[ by Victor T Trucker ]------[ 28-MAY-19 ]#===----#
#------===#[ Total Time Spent Developing This mini-Application: 48 minutes ]#===------#
#--------------------------------------------------------------------------------------#
__author__  = 'Victor T Trucker'
__appName__ = 'Color Assignment'
__version__ = '1.0.00'

#----------------------------------------------------------------------------------#
#------------------------===#[ Runtime Class Objects ]#===--------------------------#
#------------------------------------------------------------------------------------#
class square():
    name  = ''
    color = ''

    def __init__(self, myName):
        self.name = myName

    def setColor(self, myColor):
        self.color = myColor

    def getColor(self):
        return self.color

    def assignColor(self, myNeighbors):
        colors = ['red','green','blue','yellow']

        for neighbor in myNeighbors:
            usedColor = neighbor.getColor()

            if usedColor in colors:
                colors.remove(usedColor)

        self.setColor(colors[0])
        print('\n>>> %s has been assigned %s' % (self.name, self.color))

#----------------------------------------------------------------------------------#
#-----------------------------===#[ Process Body ]#===------------------------------#
#------------------------------------------------------------------------------------#
def main():
    A = square('a')
    B = square('b')
    C = square('c')
    D = square('d')
    E = square('e')
    F = square('f')

    A.assignColor([B,C,E])
    B.assignColor([A,C,D])
    C.assignColor([A,B,D,E,F])
    D.assignColor([B,C,F])
    E.assignColor([A,C,F])
    F.assignColor([E,C,D])

#----------------------------------------------------------------------------------#
#------------------------------===#[ Entry Point ]#===------------------------------#
#------------------------------------------------------------------------------------#
if __name__ == "__main__":
      main()
