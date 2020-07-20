''' Test Use Case 8 '''
#Obtain price of fuel for gas stations in a certain geographic area
type("1591045945025.png", "Corso Duca degli Abruzzi Turin Piemont Italy")
click("1591046398385.png")
click("1591046172918.png")
click(Pattern("1591047242981.png").targetOffset(-37,6))
click("1591046267171.png")
click("1591046201559.png")
click(Pattern("1591047089882.png").targetOffset(-69,5))
click("1591046267171.png")
sleep(3)
click(Pattern("1591047363619.png").targetOffset(-251,-8))