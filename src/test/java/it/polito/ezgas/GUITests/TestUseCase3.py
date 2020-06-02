''' Test Use Case 3 '''
''' Administrator can delete any account'''
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "admin@ezgas.com")
type("1590957794987.png", "admin")
click("1590957593435.png")
wait("1590958901642.png",10)
click("1590959000966.png")
wait("1590958237072.png",10)
wheel(WHEEL_DOWN, 2)
click(Pattern("1590962971188.png").similar(0.96).targetOffset(556,-10))
click(Pattern("1590962982680.png").similar(0.95).targetOffset(567,2))