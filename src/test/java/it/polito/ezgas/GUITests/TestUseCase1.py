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
if not exists("1591107269931.png"):
    popup("TEST FAILED: user not created")
else:
    popup("TEST PASSED: user created")  
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
if not exists(Pattern("1591107378197.png").similar(0.64)):
    popup("TEST FAILED: user not created")
else:
    popup("TEST PASSED: user created")
click("1590959162418.png")
type("1590959098540.png", "Name3")
type("1590959116507.png", "name3@name3.it")
type("1590959124355.png", "name3name3")
click("1590959139011.png")
if not exists(Pattern("1591107428028.png").similar(0.63)):
    popup("TEST FAILED: user not created")
else:
    popup("TEST PASSED: user created")