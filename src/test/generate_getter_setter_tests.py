# by e-caste 2020

import os
from random import choice

from secret import path  # absolute path down to ezgas directory in src of project

file_methods = {}
return_types = set()
for dir in ["dto", "entity"]:
    for file in os.listdir(os.path.join(path, dir)):
        with open(os.path.join(path, dir, file), 'r') as f:
            package = f"{dir}.{file}"
            for line in f.readlines():
                if "public" in line and "get" in line:
                    if package not in file_methods:
                        file_methods[package] = {}
                    return_type = line.split("public")[1].split(" ")[1]
                    method_name = line.split("public")[1].split(" ")[2].split("(")[0][3:]
                    file_methods[package][method_name] = return_type
                    return_types.add(return_type)

print(file_methods)
print(return_types)

imports = set()
method_calls = []
methods = {}
test_values = {
    'Integer': range(-1000, 1000),
    'double': [-1.0, 2.76, 3.14, 87348.58894, -999999.999999],
    'String': ['"test_string"', '"PoliTo"', '"string with a space"', '"§tr1n9 w/ @ w3|rD cHaRacter"'],
    'Boolean': ['true', 'false'],
    'boolean': ['true', 'false'],
}
for package in file_methods:
    imports.add(f"import it.polito.ezgas.{package.split('java')[0][:-1]};")
    class_name = package.split(".")[1][0].lower() + package.split(".")[1][1:]
    class_name_capital = package.split(".")[1]
    method_calls.append(f"\t\tGetterSetterTests.{class_name}();")
    for method_name in file_methods[package]:
        try:
            value_type = file_methods[package][method_name]
            test_value = choice(test_values[value_type])
        except KeyError:  # skip User methods
            continue
        if package not in methods:
            methods[package] = [f"\t@Test\n"
                                f"\tpublic static void {class_name}() " + "{\n"
                                f"\t\t{class_name_capital} {class_name} = new {class_name_capital}();\n"]
        methods[package].append(f"\t\t{class_name}.set{method_name}({test_value});\n"
                                f"""\t\tassert {class_name}.get{method_name}(){f'.equals({test_value})'
                                        if value_type != 'double' and value_type != 'boolean'
                                        else f' == {test_value}'};\n""")

for package in methods:
    methods[package].append("\t}")

methods_tmp = methods
methods = []
for package in methods_tmp:
    methods.append("\n".join(methods_tmp[package]))

default_content = """package it.polito.ezgas;

INSERT_IMPORTS_HERE
import org.junit.Test;

public class GetterSetterTests {

    public static void main() {
INSERT_METHOD_CALLS_HERE
    }

INSERT_METHODS_HERE
}"""

with open("GetterSetterTests.java", 'w') as out:
    out.write(default_content
              .replace("INSERT_IMPORTS_HERE", "\n".join(imports))
              .replace("INSERT_METHOD_CALLS_HERE", "\n".join(method_calls))
              .replace("INSERT_METHODS_HERE", "\n\n".join(methods)))

