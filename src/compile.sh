#!/bin/bash
export CLASSPATH=`find lib -name "*.jar" | tr '\n' ':'`
javac -cp ${CLASSPATH} Carte.java Collection.java Inventaire.java Pokemon.java Question.java Rareté.java Slot.java Pokandemaths.java