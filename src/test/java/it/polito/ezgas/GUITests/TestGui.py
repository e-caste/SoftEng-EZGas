''' Test Use Case 1 '''
click("1590956291241.png")
wait("1590958222585.png",10)
click("1590956365458.png")
wait("1590958237072.png",10)
type("1590956887378.png", "Name1")
type("1590956900964.png", "name1@name1.it")
type("1590956958751.png", "name1name1")
click("1590956498256.png")
'''User can create one account'''
wait("1590958222585.png",10)
type("1590957780572.png", "name1@name1.it")
type("1590957794987.png", "name1name1")
click("1590957593435.png")
wait("1590958901642.png",10)
''' Administrator can create many accounts'''
click("1590958901642.png")
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "admin@ezgas.com")
type("1590957794987.png", "admin")
click("1590957593435.png")
wait("1590958901642.png",10)
click("1590959000966.png")
wait("1590958237072.png",10)
type("1590959098540.png", "Name2")
type("1590959116507.png", "name2@name2.it")
type("1590959124355.png", "name2name2")
click("1590959139011.png")
click("1590959162418.png")
type("1590959098540.png", "Name3")
type("1590959116507.png", "name3@name3.it")
type("1590959124355.png", "name3name3")
click("1590959139011.png")


''' Test Use Case 3 '''
''' Administrator can delete any account'''
click(Pattern("1590962971188.png").exact().targetOffset(556,-10))
click(Pattern("1590962982680.png").similar(0.95).targetOffset(567,2))


''' Test Use Case 4 '''
''' Create Gas Station '''
while not exists(Pattern("1590963516497.png").exact()):
        type(Key.PAGE_DOWN)

type(Pattern("1590963516497.png").exact(), "GasStation1")
type(Pattern("1590963958383.png").exact(), "Corso Vittorio Emanuele II 156 Turin Piemont Italy")
click("Immagine.jpg")
click(Pattern("1590964069359.png").exact().targetOffset(175,0))
click(Pattern("1590964127927.png").exact())
click(Pattern("1590964923854.png").targetOffset(-44,2))
click("1590959139011.png")


''' Test Use Case 5 '''
''' Modify Gas Station information '''
while not exists("1590970067494.png"):
        type(Key.PAGE_DOWN)
click(Pattern("1590965349326.png").similar(0.95).targetOffset(524,-43))
while not exists("1590970113215.png"):
        type(Key.PAGE_UP)
click("1590965410440.png")
type("a", KEY_CTRL)
type(Key.BACKSPACE) or type(Key.DELETE)
type("NewNameGasStation1")
click("1590965633199.png")

click("1590964589955.png")


''' Test Use Case 7 '''
'''Report fuel price for a gas station'''
while not exists("1590966014134.png"):
    type(Key.PAGE_UP)
click("1590966014134.png")
wait("1590958901642.png",10)
click("1590958901642.png")
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "name1@name1.it")
type("1590957794987.png", "name1name1")
click("1590957593435.png")
dragDrop("1590966861970.png","1590966883292.png")
type("1590966963981.png","Corso Vittorio Emanuele II Turin Piemont Italy")
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