''' Test Use Case 7 '''
'''Report fuel price for a gas station'''
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "name1@name1.it")
type("1590957794987.png", "name1name1")
click("1590957593435.png")
dragDrop("1590966861970.png","1590966883292.png")
type("1590966963981.png","Corso Vittorio Emanuele II Turin Piemont Italy")
wait("Immagine.jpg",10)
click("Immagine.jpg")
click(Pattern("1590967011852.png").targetOffset(144,-4))
click(Pattern("1590969144879.png").targetOffset(-59,-29))
click(Pattern("1590967039032.png").targetOffset(161,-4))
click(Pattern("1590969163605.png").targetOffset(-71,1))
click("1590967389792.png")

click("1590968076184.png")
while not exists("1590968134047.png"):
        type(Key.PAGE_DOWN)
type("1590968190478.png","1.7")
type("1590968198882.png","1.3")
click("1590968134047.png")
click(Pattern("1590967389792.png").exact())