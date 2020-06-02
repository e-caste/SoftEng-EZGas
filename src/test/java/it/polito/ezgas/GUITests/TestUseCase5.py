''' Test Use Case 5 '''
''' Modify Gas Station information '''
click("1590956291241.png")
wait("1590958222585.png",10)
type("1590957780572.png", "admin@ezgas.com")
type("1590957794987.png", "admin")
click("1590957593435.png")
wait("1590958901642.png",10)
click("1590959000966.png")
wait("1590958237072.png",10)
#while not exists(Pattern("1591105309377.png").similar(0.83)):
type(Key.PAGE_DOWN*2)
click(Pattern("1591111668576.png").similar(0.94).targetOffset(538,-12))
while not exists("1590970113215.png"):
        type(Key.PAGE_UP)
click("1590965410440.png")
type("a", KEY_CTRL)
type(Key.BACKSPACE) or type(Key.DELETE)
type("NewNameGasStation1")
click("1590965633199.png")

click("1590964589955.png")