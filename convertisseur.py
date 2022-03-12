import json
import simplekml
import os
from pathlib import Path


def convertir(files_path):
    for path in os.listdir(files_path):
        full_path = os.path.join(files_path, path)
        with open(full_path) as json_file:
            try:
                data = json.load(json_file)
                kml = simplekml.Kml()
                for elm in data:
                    kml.newpoint(coords=[(elm['lng'], elm['lat'])])
                kml.save(Path(full_path).stem+".kml")

                print(Path(full_path).stem + " est enregistrer correctement comme fichier kml")
            except json.decoder.JSONDecodeError as err:
                print(Path(full_path).stem + " n'est pas un fichier json ")
                pass


convertir("data/trajectories")


