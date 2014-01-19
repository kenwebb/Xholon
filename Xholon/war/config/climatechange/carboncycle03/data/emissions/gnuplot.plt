set term svg size 2048,1536 fsize 60
set output "GlobalCarbonEmissions03.svg"

set border 1
set grid x lc rgb "#e0e0e0"
set grid y2 lc rgb "#c0c0c0"
set y2label "Metric tons of Carbon/year (Billions)"
#set title "Global Fossil Carbon Emissions"
set xtics nomirror
set y2tics
#unset  nmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm

set datafile separator ","
set key top left Left reverse

plot [1800:2010] "global.1751_2008.csv" \
           using 1:($2/1000) title "Total" lc rgb "black" lw 10 with lines, \
        "" using 1:($4/1000) title "Petroleum" lc rgb "blue" lw 8 with lines, \
        "" using 1:($5/1000) title "Coal" lc rgb "green" lw 8 with lines, \
        "" using 1:($3/1000) title "Natural gas" lc rgb "red" lw 8 with lines, \
        "" using 1:($6/1000) title "Cement production" lc rgb "cyan" lw 8 with lines, \
        "" using 1:($7/1000) title "Gas Flaring" lc rgb "gray" lw 8 with lines
