''' Test Use Case 4 '''
''' Create Gas Station '''
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "admin@ezgas.com")
type("1590957794987.png", "admin")
click("1590957593435.png")
wait("1590958901642.png",10)
click("1590959000966.png")
wait("1590958237072.png",10)
while not exists(Pattern("1590963516497.png").exact()):
        type(Key.PAGE_DOWN)

type(Pattern("1590963516497.png").exact(), "GasStation1")
type(Pattern("1590963958383.png").exact(), "Corso Vittorio Emanuele II 156 Turin Piemont Italy")
wait("Immagine.jpg",10)
click("Immagine.jpg")
click(Pattern("1590964069359.png").exact().targetOffset(175,0))
click(Pattern("1590964127927.png").exact())
click(Pattern("1590964923854.png").targetOffset(-44,2))
click("1590959139011.png")