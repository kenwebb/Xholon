let $row := $x//HexRow[1]
for $col in $row/HexGridCell
   return (count($col/Creature))
