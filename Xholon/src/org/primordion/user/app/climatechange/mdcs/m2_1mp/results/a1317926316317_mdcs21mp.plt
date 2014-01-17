# Gnuplot script file, generated from a Xholon application.
# Data file: a1317926316317_mdcs21mp.csv
reset
set output "a1317926316317_mdcs21mp.png"
set terminal png size 1024,768
set datafile separator ","
set xlabel "Time (years)"
set ylabel "Temperature (°C)"
set format y "%.0f"
set mxtics 5
set border 13
set title "Climate model with no atmosphere (1317926316317)"
set key right box 3
plot 'a1317926316317_mdcs21mp.csv' using 2 title "celsius_9" with lines 1
pause -1 "Hit return to exit"
