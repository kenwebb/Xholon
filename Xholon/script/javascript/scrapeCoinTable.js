/**
 * Scrape a list of coins form a coin site.
 * https://www.pcgs.com/SetRegistry/type-sets/complete-type-sets/complete-u-s-type-set-1792-1964/alltimeset/142775
 */

var allarr = [];
var table = document.querySelector("table#DataTables_Table_0");
var thnodes = table.firstElementChild.querySelectorAll("th"); // table header nodes (column labels)
var tharr = Object.getOwnPropertyNames(thnodes);
var thlabelarr = [];
tharr.forEach(function(index) {
  switch (Number(index)) {
  case 2:
  case 4:
  case 5:
  case 6:
    var label = thnodes[tharr[index]].textContent;
    thlabelarr.push(label);
    break;
  default: break;
  }
});
allarr.push(thlabelarr);

var trnodes = table.firstElementChild.nextElementSibling.querySelectorAll("tr");
var trarr = Object.getOwnPropertyNames(trnodes);

trarr.forEach(function(rindex) {
  var row = trnodes[trarr[rindex]];
  var tdnodes = row.querySelectorAll("td");
  var tdarr = Object.getOwnPropertyNames(tdnodes);
  var tdvaluearr = [];
  tharr.forEach(function(index) {
    switch (Number(index)) {
    case 2:
    case 4:
    case 5:
    case 6:
      var value = tdnodes[tdarr[index]].textContent;
      tdvaluearr.push(value);
      break;
    default: break;
    }
  });
  allarr.push(tdvaluearr);
});

console.log(allarr);
console.log(JSON.stringify(allarr, null, 2));

/* result
[
  ["Item", "Date", "Denom", "Grade"],
  ["Liberty Cap Left Half Cent (1793)", "1793", "1/2C", "MS65BN"],
  ["Liberty Cap Rt Half Cent Lg Hd (1794)", "1794", "1/2C", "MS67RB"],
  ["Liberty Cap Rt Half Cent Sm Hd (1795-1797)", "1796", "1/2C", "MS67RB"],
  ["Draped Bust Half Cent (1800-1808)", "1804", "1/2C", "MS64+ RD"],
  ["Classic Head Half Cent (1809-1836)", "1811", "1/2C", "MS66RB"],
  ["Braided Hair Half Cent (1840-1857)", "1855", "1/2C", "MS65RD"],
  ["Chain Cent (1793)", "1793 Chain", "1C", "MS66BN"],
  ["Wreath Cent (1793)", "1793 Wreath", "1C", "MS66RB"],
  ["Liberty Cap Cent, Beaded Border (1793)", "1793", "1C", "MS62+ BN"],
  ["Liberty Cap Cent, Dent. Border (1794-1796)", "1796", "1C", "MS66+ RB"],
  ["Draped Bust Cent (1796-1807)", "1807/6", "1C", "MS66RD"],
  ["Classic Head Cent (1808-1814)", "1811", "1C", "MS65RD"],
  ["Coronet Head Cent (1816-1839)", "1833", "1C", "MS66RB"],
  ["Braided Hair Cent (1839-1857)", "1855", "1C", "MS66RD"],
  ["Flying Eagle Cent (1856-1858)", "1858", "1C", "MS66+"],
  ["Indian Cent Cop Nic, No Shield (1859)", "1859", "1C", "PR67"],
  ["Indian Cent Cop Nic (1860-1864)", "1862", "1C", "MS67"],
  ["Indian Cent Bronze (1864-1909)", "1899", "1C", "MS67+ RD"],
  ["Lincoln Cent, Wheat (1909-1958)", "1934", "1C", "MS68RD"],
  ["Lincoln Cent, Steel (1943)", "1943-D", "1C", "MS68"],
  ["Lincoln Cent, Memorial (1959-1964)", "1959", "1C", "MS67RD"],
  ["Two Cents (1864-1873)", "1864", "2C", "MS66RD"],
  ["Three Cent Silver, Type 1 (1851-1853)", "1851", "3CS", "MS67+"],
  ["Three Cent Silver, Type 2 (1854-1858)", "1854", "3CS", "MS66+"],
  ["Three Cent Silver, Type 3 (1859-1873)", "1862/1", "3CS", "MS67"],
  ["Nickel Three Cents (1865-1889)", "1881", "3CN", "PR68CAM"],
  ["Shield Nickel, Rays (1866-1867)", "1867", "5C", "MS66+"],
  ["Shield Nickel, No Rays (1867-1883)", "1876", "5C", "PR67+ CAM"],
  ["Liberty Head Nickel, No \"CENTS\" (1883)", "1883", "5C", "MS67"],
  ["Liberty Head Nickel (1883-1913)", "1893", "5C", "PR67DCAM"],
  ["Buffalo Nickel, Type 1 (1913)", "1913", "5C", "MS68"],
  ["Buffalo Nickel, Type 2 (1913-1938)", "1938-D", "5C", "MS67+"],
  ["Jefferson Nickel (1938-1964)", "1950-D", "5C", "MS67FS"],
  ["Jefferson Nickel, Wartime (1942-1945)", "1943-D", "5C", "MS67+ FS"],
  ["Bust Half Disme (1792)", "1792", "H10C", "MS66"],
  ["Flowing Hair Half Dime (1794-1795)", "1795", "H10C", "MS67"],
  ["Draped Bust Half Dime, Small Eagle (1796-1797)", "1797", "H10C", "MS66"],
  ["Draped Bust Half Dime, Large Eagle (1800-1805)", "1800", "H10C", "MS66"],
  ["Capped Bust Half Dime (1829-1837)", "1835", "H10C", "MS68"],
  ["Liberty Seated Half Dime, No Stars (1837-1838)", "1837", "H10C", "MS67"],
  ["Liberty Seated Half Dime, No Drapery (1838-1840)", "1838", "H10C", "MS68+"],
  ["Liberty Seated Half Dime, Drapery (1840-1859)", "1859", "H10C", "MS68+"],
  ["Liberty Seated Half Dime, Arrows (1853-1855)", "1854", "H10C", "MS67"],
  ["Liberty Seated Half Dime, Legend (1860-1873)", "1862", "H10C", "MS68"],
  ["Draped Bust Dime, Small Eagle (1796-1797)", "1796", "10C", "MS66"],
  ["Draped Bust Dime, Large Eagle (1798-1807)", "1805", "10C", "MS67"],
  ["Capped Bust Dime, Large Denticles (1809-1828)", "1814", "10C", "MS66"],
  ["Capped Bust Dime, Small Denticles (1828-1837)", "1829", "10C", "MS67"],
  ["Liberty Seated Dime, No Stars (1837-1838)", "1837", "10C", "MS66"],
  ["Liberty Seated Dime, No Drapery (1838-1840)", "1839", "10C", "MS67"],
  ["Liberty Seated Dime, Drapery (1840-1860)", "1852", "10C", "MS67+"],
  ["Liberty Seated Dime, Arrows (1853-1855)", "1853", "10C", "MS66"],
  ["Liberty Seated Dime, Legend (1860-1891)", "1873", "10C", "PR67DCAM"],
  ["Liberty Seated Dime, Legend, Arrows (1873-1874)", "1874", "10C", "MS68"],
  ["Barber Dime (1892-1916)", "1892", "10C", "MS67+"],
  ["Mercury Dime (1916-1945)", "1939-D", "10C", "MS68+ FB"],
  ["Roosevelt Dime (1946-1964)", "1947-S", "10C", "MS68"],
  ["Twenty Cents (1875-1878)", "1876", "20C", "MS67+"],
  ["Draped Bust Quarter, Small Eagle (1796)", "1796", "25C", "MS66"],
  ["Draped Bust Quarter, Large Eagle (1804-1807)", "1806/5", "25C", "MS65+"],
  ["Capped Bust Quarter, Large Size (1815-1828)", "1827/3", "25C", "PR66+ CAM"],
  ["Capped Bust Quarter, Small Size (1831-1838)", "1837", "25C", "MS67"],
  ["Liberty Seated Quarter, No Drapery (1838-1840)", "1839", "25C", "MS66+"],
  ["Liberty Seated Quarter, No Motto (1840-1865)", "1853", "25C", "MS67"],
  ["Liberty Seated Quarter, Arr & Rays (1853)", "1853", "25C", "MS66"],
  ["Liberty Seated Quarter, Arrows NM (1854-1855)", "1854", "25C", "MS67"],
  ["Liberty Seated Quarter, With Motto (1866-1891)", "1886", "25C", "PR67DCAM"],
  ["Liberty Seated Quarter, Motto, Arr (1873-1874)", "1874", "25C", "PR67CAM"],
  ["Barber Quarter (1892-1916)", "1892", "25C", "PR68DCAM"],
  ["Standing Liberty Quarter, Type 1 (1916-1917)", "1917-D", "25C", "MS67FH"],
  ["Standing Liberty Quarter, Type 2 (1917-1930)", "1928-S", "25C", "MS67FH"],
  ["Washington Quarter, Silver (1932-1964)", "1956", "25C", "PR69DCAM"],
  ["Flowing Hair Half Dollar (1794-1795)", "1795", "50C", "MS63+"],
  ["Draped Bust Half Dollar, Sm Eagle (1796-1797)", "1797", "50C", "MS65+"],
  ["Draped Bust Half Dollar, Lg Eagle (1801-1807)", "1806/5", "50C", "MS66"],
  ["Capped Bust Half Dollar, Let Edge (1807-1836)", "1835", "50C", "MS67+"],
  ["Capped Bust Half Dollar, \"50 CENTS\" (1836-1837)", "1837", "50C", "MS67"],
  ["Capped Bust Half Dollar, \"HALF DOL.\" (1838-1839)", "1839-O", "50C", "MS66"],
  ["Liberty Seated Half Dollar, No Drap (1839)", "1839", "50C", "MS65"],
  ["Liberty Seated Half Dollar, No Motto (1839-1866)", "1865", "50C", "PR66+ CAM"],
  ["Liberty Seated Half Dollar, Arr & Ray (1853)", "1853", "50C", "MS67"],
  ["Liberty Seated Half Dollar, NM, Arr (1854-1855)", "1855", "50C", "MS66"],
  ["Liberty Seated Half Dollar, Motto (1866-1891)", "1880", "50C", "MS67+"],
  ["Liberty Seated Half Dollar, Mot, Arr (1873-1874)", "1874", "50C", "MS67+"],
  ["Barber Half Dollar (1892-1915)", "1897", "50C", "PR68CAM"],
  ["Walking Liberty Half Dollar (1916-1947)", "1942", "50C", "MS67+"],
  ["Franklin Half Dollar (1948-1963)", "1956", "50C", "PR69DCAM"],
  ["Kennedy Half Dollar (1964)", "1964", "50C", "PR69DCAM"],
  ["Flowing Hair Dollar (1794-1795)", "1795", "$1", "MS66"],
  ["Bust Dollar, Small Eagle (1795-1798)", "1795", "$1", "MS66"],
  ["Bust Dollar, Large Eagle (1798-1804)", "1802", "$1", "MS65"],
  ["Liberty Seated Dollar, Gob NS Obv (1836)", "1836", "$1", "PR65+"],
  ["Liberty Seated Dollar, Gob ST Obv (1838-1839)", "1838", "$1", "PR64+"],
  ["Liberty Seated Dollar, No Motto (1840-1866)", "1855", "$1", "PR66CAM"],
  ["Liberty Seated Dollar, With Motto (1866-1873)", "1868", "$1", "PR66DCAM"],
  ["Trade Dollar (1873-1885)", "1878-S", "T$1", "MS67"],
  ["Morgan Dollar (1878-1921)", "1880-S", "$1", "MS68+"],
  ["Peace Dollar, High Relief (1921-1922)", "1921", "$1", "MS66"],
  ["Peace Dollar (1922-1935)", "1923", "$1", "MS67"],
  ["Gold Dollar, Type 1 (1849-1854)", "1849", "G$1", "MS69"],
  ["Gold Dollar, Type 2 (1854-1856)", "1854", "G$1", "MS66+"],
  ["Gold Dollar, Type 3 (1856-1889)", "1885", "G$1", "PR67+ DCAM"],
  ["Draped Bust $2 1/2, No Stars (1796)", "1796", "$2.50", "MS65"],
  ["Draped Bust $2 1/2, Stars (1796-1807)", "1798", "$2.50", "MS65"],
  ["Capped Bust $2 1/2, Large Bust (1808)", "1808", "$2.50", "MS65"],
  ["Capped Bust $2 1/2, Lg Dent. (1821-1827)", "1821", "$2.50", "MS66+"],
  ["Capped Bust $2 1/2, Sm Dent. (1829-1834)", "1829", "$2.50", "MS67"],
  ["Classic Head $2 1/2 (1834-1839)", "1838", "$2.50", "MS66+"],
  ["Liberty $2 1/2 (1840-1907)", "1898", "$2.50", "PR68DCAM"],
  ["Indian $2 1/2 (1908-1929)", "1910", "$2.50", "MS66"],
  ["Indian Princess $3 (1854-1889)", "1886", "$3", "PR67DCAM"],
  ["Flowing Hair Stella (1879-1880)", "1880", "$4", "PR66"],
  ["Coiled Hair Stella (1879-1880)", "1880", "$4", "PR65CAM"],
  ["Draped Bust $5, Small Eagle (1795-1798)", "1795", "$5", "MS65"],
  ["Draped Bust $5, Large Eagle (1795-1807)", "1806", "$5", "MS64+"],
  ["Capped Bust $5, Large Bust (1807-1812)", "1811", "$5", "MS66"],
  ["Capped Bust $5, Sm Bust, Lg Sz (1813-1829)", "1820", "$5", "MS66+"],
  ["Capped Bust $5, Sm Bust, Sm Sz (1829-1834)", "1833", "$5", "PR67+ CAM"],
  ["Classic Head $5 (1834-1838)", "1838", "$5", "MS65+"],
  ["Liberty $5, No Motto (1839-1866)", "1865", "$5", "PR65CAM"],
  ["Liberty $5, With Motto (1866-1908)", "1900", "$5", "PR68+ DCAM"],
  ["Indian Head $5 (1908-1929)", "1910-D", "$5", "MS67"],
  ["Draped Bust $10, Small Eagle (1795-1797)", "1795", "$10", "MS66+"],
  ["Draped Bust $10, Large Eagle (1797-1804)", "1799", "$10", "MS65+"],
  ["Liberty $10, No Mot, Cov. Ear (1838-1839)", "1838", "$10", "PR65CAM"],
  ["Liberty $10, No Motto (1839-1866)", "1860", "$10", "PR65CAM"],
  ["Liberty $10, With Motto (1866-1907)", "1900", "$10", "PR68DCAM"],
  ["Indian $10, Wire Edge (1907)", "1907", "$10", "MS67"],
  ["Indian $10, Rolled Edge (1907)", "1907", "$10", "MS66+"],
  ["Indian $10, No Motto (1907-1908)", "1907", "$10", "MS67+"],
  ["Indian $10, With Motto (1908-1933)", "1911", "$10", "MS67"],
  ["Liberty $20, No Motto (1850-1866)", "1857-S", "$20", "MS66"],
  ["Liberty $20, \"TWENTY D.\" (1866-1876)", "1876-S", "$20", "MS64"],
  ["Liberty $20, \"TWENTY DOLLARS\" (1877-1907)", "1886", "$20", "PR67DCAM"],
  ["St. Gaudens $20, High Relief (1907)", "1907", "$20", "MS66+"],
  ["St. Gaudens $20, No Motto (1907-1908)", "1908", "$20", "MS68"],
  ["St. Gaudens $20, Motto (1908-1933)", "1928", "$20", "MS67"]
]
*/

