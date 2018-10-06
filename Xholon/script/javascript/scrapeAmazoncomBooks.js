/**
 * Scrape list of books from amazon.com
 * Ken Webb
 * April 29, 2018
 */
var $wnd = window;
var $doc = document;
var root = $doc.querySelector("#recs > div:nth-child(1)"); // possibly multiple pages (1) (2) etc.
//$wnd.console.log(root);
var book = root.firstElementChild;
var titlePos = "Go to the product page for ".length;
while (book) {
  var btitle = book.firstElementChild.title.substring(titlePos);
  // "Go to the product page for Systems Thinking For Social Change: A Practical Guide to Solving Complex Problems, Avoiding Unintended Consequences, and Achieving Lasting Results"
  $wnd.console.log(btitle);
  var bauthor = book.textContent.trim();
  // Systems Thinking...           by David Peter Stroh    (54)      $21.29
  $wnd.console.log(" " + bauthor);
  book = book.nextElementSibling;
}

