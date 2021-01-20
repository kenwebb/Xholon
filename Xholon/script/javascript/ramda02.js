/*  YES
const removeNulls = R.when(
  R.is(Object),
  R.pipe(
    R.reject(R.isNil),
    R.map(a => removeNulls(a))
  )
)
removeNulls(['a',null,'c']);
*/

// NO
const int2bin = (n, arr) => R.when(
  R.gt(n,0),
  R.pipe(
    console.log(n),
    R.append(R.modulo(n,2), arr),
    //R.map(int2bin(Math.floor(R.divide(n,2)), arr))
    int2bin(Math.floor(R.divide(n,2)), arr),
    console.log(arr)
  )
)

int2bin(8,[])


// OK
// int2bin :: Int -> [Bit]
const int2bin = n => int2binRecurse(n, [])
const int2binRecurse = (n, arr) => {
  if (R.equals(n,0)) {
    return arr
  }
  else {
    arr.push(R.modulo(n, 2))
    return int2binRecurse(Math.floor(R.divide(n,2)), arr)
  }
}

int2bin(255);


-------------------------------
/*
const removeNulls = R.when(
  R.is(Object),
  R.pipe(
    R.reject(R.isNil),
    R.map(a => removeNulls(a))
  )
)
removeNulls(['a',null,'c']);
*/

/*
const int2bin = (n, arr) => R.when(
  R.gt(n,0),
  R.pipe(
    console.log(n),
    R.append(R.modulo(n,2), arr),
    //R.map(int2bin(Math.floor(R.divide(n,2)), arr))
    int2bin(Math.floor(R.divide(n,2)), arr),
    console.log(arr)
  )
)

int2bin(8,[])
*/

// int2bin :: Int -> [Bit]
const int2bin = n => int2binRecurse(n, [])
const int2binRecurse = (n, arr) => {
  if (R.equals(n,0)) {
    return arr
  }
  else {
    arr.push(R.modulo(n, 2)) // YES
    //R.prepend(R.modulo(n, 2), arr) // NO
    //R.prepend(R.modulo(n, 2))([arr]) // NO
    return int2binRecurse(Math.floor(R.divide(n,2)), arr)
  }
}

int2bin(10);

// OK
//R.compose(R.prepend(R.modulo(13,2)))([9])

/* OK
const int2bin = (n, arr) => {
  if (R.equals(n,0)) {
    return R.identity(arr)
  }
  else {
    return int2bin(Math.floor(R.divide(n,2)), R.prepend(R.modulo(n,2), arr))
  }
}
int2bin(15,[])
*/

// OK
/*
const int2bin = R.curry((n, arr) => {
  if (R.equals(n,0)) {
    return R.identity(arr)
  }
  else {
    return int2bin(Math.floor(R.divide(n,2)), R.prepend(R.modulo(n,2), arr))
  }
})
//int2bin(15,[]) // OK
int2bin(15)([]) // also OK
*/

const int2bin = R.curry((n, arr) => {
  if (R.equals(n,0)) {
    console.log(n)
    return R.identity(arr)
  }
  else {
    console.log(n)
    return int2bin(Math.floor(R.divide(n,2)))(R.prepend(R.modulo(n,2), arr)) 
  }
})
console.log(int2bin()) 
console.log(int2bin(10))
console.log(int2bin(10)([])) // OK


