# Gnuplot script file, generated from a Xholon application.
# Data file: a1544706803278_stats.csv
# see: http://gnuplot.respawned.com/
set output "out.svg"
set terminal svg size 2048,768 enhanced fname 'arial' fsize 10 butt solid
set datafile separator ","
set xlabel "Time (YYYYdddh)"
set ylabel "children moving between places"
set format y "%.0f"
set mxtics 5
set border 13
set title "BGCO Member Attendance (1544706803278)"
set key right box 3
plot "data.txt" using 2 title "Andrea:member_47" with lines, \
     "data.txt" using 3 title "Tony:member_48" with lines, \
     "data.txt" using 4 title "Freddie:member_1057" with lines, \
     "data.txt" using 5 title "Betty:member_1067" with lines

