// https://www.crockford.com/little.js
// The Little JavaScripter

// Ken Webb, Jan 12, 2021 - my version using arrow notation

((title) => {
// Little Scheme primitives
const add1 = n => n + 1
const car = s => s[0]
const cdr = s => s[1]
const cons = (a, d) => [a, d]
const isAtom = a => typeof a === "string" || typeof a === "number" || typeof a === "boolean"
const isBoolean = a => typeof a === "boolean"
const isNull = a => a === undefined || a === null
const isEq = (s, t) => s === t || (isNull(s) && isNull(t))
const isFunction = a => typeof a === "function"
const isList = a => Array.isArray(a)
const isNumber = a => Number.isFinite(a)
const isUndefined = a => a === undefined
const isZero = s => s === 0
const sub1 = n => n -1

// Produce a printable presentation of an s-expression
const p = x => {
  var r;
  if (isList(x)) {
    r = "(";
    do {
      r += p(car(x)) + " ";
      x = cdr(x);
    } while (isList(x));
    if (r.charAt(r.length - 1) === " ") {
      r = r.substr(0, r.length - 1);
    }
    if (isAtom(x)) {
      r += " . " + x;
    }
    return r + ")";
  }
  if (isNull(x)) {
    return "()";
  }
  return x;
}

var rx_token = /\s*([\(\)']|[^\s()']+)?/gmy;

// Produce an array of s-expressions from a source string.
const s = source => {
  var result = [];
  var expr;
  var num;
  rx_token.lastIndex = 0;
  return (function array() {
    expr = (function expression() {
      var head = null;
      var neo = null;
      var match = rx_token.exec(source);
      var sexp = (match && match[1]) || "";
      var tail = null;
      if (sexp === "(") {
        while (true) {
          sexp = expression();
          if (sexp === "" || sexp === ")") {
            return head;
          }
          neo = [sexp];
          if (tail) {
            tail[1] = neo;
          } else {
            tail = neo;
            head = neo;
          }
          tail = neo;
        }
      } else if (!sexp) {
        sexp = source.slice(rx_token.lastIndex);
        if (sexp) {
          rx_token.lastIndex = source.length;
          return "ERROR: " + sexp;
        } else {
          return "";
        }
      } else if (sexp === "'") {
        return ["quote", [expression()]];
      } else {
        num = Number(sexp);
        return (
          (Number.isFinite(num) && sexp.length > 0)
          ? num
          : sexp
        );
      }
    }());
    if (expr) {
      result.push(expr);
      return array();
    } else {
      return result;
    }
  }())
};


// Chapter Two
const isLat = s => isNull(s) || (isAtom(car(s)) && isLat(cdr(s)))
const isMember = (a, lat) => isNull(lat) ? false : isEq(a, car(lat)) || isMember(a, cdr(lat))

// Chapter Three
const rember = (a, lat) =>
  isNull(lat)
  ? null
  : (
    isEq(a, car(lat))
    ? cdr(lat)
    : cons(car(lat), rember(a, cdr(lat)))
  )

const firsts = l => isNull(l) ? null : cons(car(car(l)), firsts(cdr(l)))

const insertR = (neo, old, lat) =>
  isNull(lat)
  ? null
  : (
    cons(car(lat), isEq(car(lat), old)
    ? cons(neo, cdr(lat))
    : insertR(neo, old, cdr(lat)))
  )

const insertL = (neo, old, lat) =>
  isNull(lat)
  ? null
  : (
    isEq(car(lat), old)
    ? cons(neo, lat)
    : cons(car(lat), insertL(neo, old, cdr(lat)))
  )

// testing KSW
console.log(add1(5))
console.log(sub1(5))
console.log(isAtom(5))
console.log(isAtom("five"))
console.log(isAtom(true))
console.log(isAtom([]))
console.log(isAtom({}))


})()

