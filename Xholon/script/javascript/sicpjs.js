/*
SICP JS
Ken Webb
January 3, 2021

https://en.wikipedia.org/wiki/Structure_and_Interpretation_of_Computer_Programs,_JavaScript_Adaptation
https://en.wikipedia.org/wiki/Source_(programming_language)
https://source-academy.github.io/sicp/index.html
https://source-academy.github.io/sicp/split/
https://github.com/source-academy/sicp
https://www.reddit.com/r/scheme/comments/ea1f8w/sicp_js_going_public/

see also:
https://www.crockford.com/little.html
https://www.crockford.com/scheme.html
https://www.crockford.com/little.js
https://pdfs.semanticscholar.org/35d0/d5275a8390c351ce98fbdc2ad37d210ba63b.pdf

*/

// SICP JS 1.1.5
((title) => {
  const square = x => x * x;
  const sum_of_squares = (x, y) => square(x) + square(y);
  const f = a => sum_of_squares(a + 1, a * 2);
  console.log(f(5)); // 136
})("SICP JS 1.1.5")

// SICP JS 1.1.6
((title) => {
  const plus = (a, b) => a + b
  const minus = (a, b) => a - b
  const a_plus_abs_b = (a, b) => (b >= 0 ? plus : minus)(a, b)
  console.log(a_plus_abs_b(4, 5)); // 9
  console.log(a_plus_abs_b(4, 0)); // 4
  console.log(a_plus_abs_b(4, -5)); // 9
})("SICP JS 1.1.6")

// SICP JS 1.1.7
((title) => {
  const conditional = (predicate, then_clause, else_clause) => predicate ? then_clause : else_clause
  console.log(conditional(2 === 3, 0, 5)) // 5
  console.log(conditional(1 === 1, 0, 5)) // 0
})("SICP JS 1.1.7")

// SICP JS 1.1.8
((title) => {
  const abs = x => x >= 0 ? x : - x;
  const square = x => x * x;
  const average = (x, y) => (x + y) / 2;
  const sqrt = x => {
    const is_good_enough = guess => abs(square(guess) - x) < 0.001;
    const improve = guess => average(guess, x / guess);
    const sqrt_iter = guess => is_good_enough(guess) ? guess : sqrt_iter(improve(guess));
    return sqrt_iter(1);
  };
  
  console.log(sqrt(5)); // 2.2360688956433634
})("SICP JS 1.1.8")

// SICP JS 1.2.2  Tree Recursion
((title) => {
  const fib = n => fib_iter(1, 0, n)
  const fib_iter = (a, b, count) => count === 0 ? b : fib_iter(a + b, a, count - 1)
  console.log(fib(15)) // 610
})("SICP JS 1.2.2  Tree Recursion")

// SICP JS 1.2.5  Greatest Common Divisors
((title) => {
  const gcd = (a, b) => b === 0 ? a : gcd(b, a % b)
  console.log(gcd(16, 28)) // 4
})("SICP JS 1.2.5  Greatest Common Divisors")

// SICP JS 1.2.6  Example: Testing for Primality - Searching for divisors
((title) => {
  const square = x => x * x;
  const smallest_divisor = n => find_divisor(n, 2);
  const find_divisor = (n, test_divisor) =>
    square(test_divisor) > n
    ? n
    : divides(test_divisor, n)
    ? test_divisor
    : find_divisor(n, test_divisor + 1)
  const divides = (a, b) => b % a === 0
  console.log(smallest_divisor(36)) // 2
  console.log(smallest_divisor(37)) // 37
  // "We can test whether a number is prime as follows: n is prime if and only if n is its own smallest divisor."
  const is_prime = n => n === smallest_divisor(n)
  console.log(is_prime(36))
  console.log(is_prime(37))
})("SICP JS 1.2.6  Example: Testing for Primality - Searching for divisors")

// SICP JS 1.3  Formulating Abstractions with Higher-Order Functions
((title) => {
  const cube = x => x * x * x
})("SICP JS 1.3  Formulating Abstractions with Higher-Order Functions")

// SICP JS 1.3.1  Functions as Arguments
((title) => {
  const sum_integers = (a, b) => a > b ? 0 : a + sum_integers(a + 1, b)
  const sum_cubes = (a, b) => a > b ? 0 : cube(a) + sum_cubes(a + 1, b)
  const pi_sum = (a, b) => a > b ? 0 : 1 / (a * (a + 2)) + pi_sum(a + 4, b)
  
  const sum = (term, a, next, b) => a > b ? 0 : term(a) + sum(term, next(a), next, b)
  const inc = n => n + 1
  const identity = x => x
  const sum_ints = (a, b) => sum(identity, a, inc, b)
  console.log(sum_ints(1, 10)) // 55
})("SICP JS 1.3.1  Functions as Arguments")

// SICP JS 2  Building Abstractions with Data
((title) => {
  // (define (linear_combination a b x y) (+ (* a x) (* b y)))
  const linear_combination1 = (a, b, x, y) => a * x + b * y
  const add = (a, b) => a + b
  const mul = (a, b) => a * b
  // (define (linear_combination a b x y) (add (mul a x) (mul b y)))
  const linear_combination2 = (a, b, x, y) => add(mul(a, x), mul(b, y))
  console.log(linear_combination1(1, 2, 3, 4)) // 11
  console.log(linear_combination2(1, 2, 3, 4)) // 11
})("SICP JS 2  Building Abstractions with Data")

// SICP JS 2.1.2
((title) => {
  const pair = (x, y) => (m => m === 0 ? x : m === 1 ? y : error(m, "argument not 0 or 1 -- pair"));
  const head = z => z(0);
  const tail = z => z(1);
  const gcd = (a, b) => b === 0 ? a : gcd(b, a % b)
  const make_rat(n, d) => pair(n, d)
  const numer = x => head(x) / gcd(head(x), tail(x))
  const denom = x => tail(x) / gcd(head(x), tail(x))
  const print_rat = x => display(stringify(numer(x)) + "/" + stringify(denom(x)))
  const ONE_HALF = make_rat(1, 2)
  print_rat(ONE_HALF)
  console.log();
})("SICP JS 2.1.2")

// SICP JS 2.1.3
((title) => {
  const pair = (x, y) => (m => m === 0 ? x : m === 1 ? y : error(m, "argument not 0 or 1 -- pair"));
  const head = z => z(0);
  const tail = z => z(1);
  
  const x = pair(1, 2);
  console.log(x);
  console.log(head(x));
})("SICP JS 2.1.3")

// SICP JS 2.2.3
((title) => {
  const pair = (x, y) => (m => m === 0 ? x : m === 1 ? y : error(m, "argument not 0 or 1 -- pair"))
  const enumerate_interval = (low, high) => low > high ? null : pair(low, enumerate_interval(low + 1, high))
  console.log(enumerate_interval(2, 7))
})("SICP JS 2.2.3")

// SICP JS 3.2.2
((title) => {
  console.log(title)
  const factorial = n => n === 1 ? 1 : n * factorial(n - 1)
  console.log(factorial(5))
})("SICP JS 3.2.2")

// SICP JS 3.3.2  TODO
((title) => {
  const front_ptr = (queue) => head(queue);
  const rear_ptr = (queue) => tail(queue);
  const set_front_ptr = (queue, item) => set_head(queue, item);
  const set_rear_ptr = (queue, item) => set_tail(queue, item);
  
  const is_empty_queue = (queue) => is_null(front_ptr(queue));
  
  const make_queue = () => pair(null, null);
  
  const insert_queue = (queue, item) => {
    const new_pair = pair(item, null);
    if (is_empty_queue(queue)) {
        set_front_ptr(queue, new_pair);
        set_rear_ptr(queue, new_pair);
    } else {
        set_tail(rear_ptr(queue), new_pair);
        set_rear_ptr(queue, new_pair);
    }
    return queue;
  }
  
  const delete_queue = (queue) => {
    if (is_empty_queue(queue)) {
        error(queue, "delete_queue called with an empty queue");
    } else {
        set_front_ptr(queue, tail(front_ptr(queue)));
        return queue;
    }
  }
  
  const q1 = make_queue();
  insert_queue(q1, "a");    
  insert_queue(q1, "b");    
  delete_queue(q1);
  delete_queue(q1);
})("SICP JS 3.3.2")

