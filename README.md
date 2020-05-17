# Java-Time-Tracker
En aquest repositori es pot trobar el codi corresponent a una aplicació de Time Tracker (Java).

Aquest repositori conté part d'un projecte realitzat en Java per a l'assignatura de Software Design (DS),
UAB. En aquest se'ns planteja proporcionar l'estructura interna d'una aplicació Time Tracker feta amb java a la qual finalment se li assignarà
una interfície d'usuari amb Android Studio (per això la ruta interna de carpetes on podem trobar els .java). El codi ha estat dissenyat complint diferents aspectes apresos durant l'assignatura com
l'ús de diferents tipus de Desgin Patterns com pot ser el patró Singleton, el patró Composite, el patró observer (actualment deprecated, però no durant
la realització d'aquesta pràctica) o el patró Visitor, entre d'altres. També implementa una petita part de Design by contract, així com l'ús d'un
checkstyle.

Les funcionalitats bàsiques d'aquesta aplicació són:
  -Creació de projectes on poder crear altres subprojectes o tasques les quals seran cronometrades una vegada s'inicien o s'apaguen (instant de temps conegut com a interval).
  -Comptador de temps de forma jerarquica (el projecte pare tindrà la suma de tots el temps invertit pels seus projectes/tasques filles)
  -Cada projecte té un nom i, optativament, una descripció (nota explicativa).
  -Una tasca preprogramada, que s'inicia automàticament en una hora donada.
  -Possibilitat de cronometrar més d'una tasca a l'hora.
  -Possibilitat de poder treure diferents tipus d'informes dels projectes. En aquest cas en trobem dos: informe detallat i informe breu. Cada tipus d'informe pot ésser extret en format TXT o HTMl (llibreries proporcionades pel professorat)
  Gràcies a l'estructura de l'aplicació es poden crear nou tipus d'informes o formes d'extreure aquests amb facilitat.
