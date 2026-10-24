"""
netzplan_check.py

Python script to check the input values for a simple task plan according to the "Netzplantechnik".

Copyright (c) 2025-2025 HS Emden/Leer
All Rights Reserved.

version 1.10 - 29 Apr 2025 - GJV - check input file version 
version 1.00 - 04 Mar 2025 - GJV - initial version 

author: Gert Veltink (GJV) - gert.veltink@hs-emden-leer.de
"""

import os
import sys

import ast
import pandas as pd

DEFAULT_FILE = "netzplan.xlsx"  # Name der Excel-Datei
COMPARE_ADDITION = "_compared"   # Erweiterung der aktualisierten Datei


def read_all_tasks_from_excel(file_path):
    """
    Reads the tasks, network and proposed solution from the Excel file provided

    :param file_path: a path to a properly formatted Excel file
    """
    df = pd.read_excel(file_path)
    tasks = {}
    for _, row in df.iterrows():
        vorgaenger = row["vorgaenger"] if pd.notna(row["vorgaenger"]) else ""
        vorgaenger = ",".join(str(x) for x in ast.literal_eval(vorgaenger))
        nachfolger = row["nachfolger"] if pd.notna(row["nachfolger"]) else ""
        nachfolger = ",".join(str(x) for x in ast.literal_eval(nachfolger))
        task = {
            "nummer": row["nummer"],
            "name": row["name"],
            "dauer": row["dauer"],
            "vorgaenger": [int(v) for v in vorgaenger.split(",") if v] if vorgaenger else [],
            "nachfolger": [int(n) for n in nachfolger.split(",") if n] if nachfolger else [],
            "FAZ": row["FAZ"] if pd.notna(row["FAZ"]) else None,
            "FEZ": row["FEZ"] if pd.notna(row["FEZ"]) else None,
            "SAZ": row["SAZ"] if pd.notna(row["SAZ"]) else None,
            "SEZ": row["SEZ"] if pd.notna(row["SEZ"]) else None,
            "GP": row["GP"] if pd.notna(row["GP"]) else None,
            "FP": row["FP"] if pd.notna(row["FP"]) else None,
            "FRP": row["FRP"] if pd.notna(row["FRP"]) else None,
            "UP": row["UP"] if pd.notna(row["UP"]) else None,
            "CT": row["CT"] if pd.notna(row["CT"]) else "",
        }
        tasks[task["nummer"]] = task
    return tasks


def write_tasks_to_excel(file_path, tasks):
    """
    Writes the updated information back to an Excel file.

    :param file_path: the path to the Excel file to be written
    :param tasks: the dictionary with all information about tasks
    """
    df = pd.DataFrame.from_records(list(tasks.values()))
    df.to_excel(file_path, index=False)


def print_tasks(tasks):
    """
    Writes the updated information to the console.

    :param tasks: the dictionary with all information about tasks
    """
    # df = pd.DataFrame.from_records(list(tasks.values()))
    # pd.set_option('display.max_columns', None)
    # print(df)

    print("---")
    for taskid in tasks.values():
        print(taskid)
    print("---\n")


# The main entry point.

def main():
    """
    The main entry for the netzplan application.

    The application can be called with one file argument.
    This argument is the input file in Excel format.

    If no input file is provided, the name "netzplan.xlsx" is assumed.
    """
    input_file = DEFAULT_FILE

    if len(sys.argv) - 1 > 1:  # more than argument?
        print("usage: " + sys.argv[0] + " [input_file]")
        exit()

    if len(sys.argv) - 1 == 1:  # one argument?
        input_file = sys.argv[1] # use argument form command line instead

    # print("checking path: '" + path + "'")
    if not os.path.exists(input_file):  # not a valid path?
        print("not a valid path: '" + input_file + "'")
        exit()  # bail out

    name, ext = os.path.splitext(input_file)
    compare_file = f"{name}{COMPARE_ADDITION}{ext}"

    solution_dict = read_all_tasks_from_excel(input_file)

    # print_tasks(solution_dict)
    write_tasks_to_excel(compare_file, solution_dict)
    print("input file: '" + input_file + "' processed.")


if __name__ == "__main__":
    main()
