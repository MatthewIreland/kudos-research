open OUnit2;;

Random.self_init ();;

(* Returns the nth item (starting from 1) from the given lazy list *)

let rec nth n l = if n > 1 then nth (n - 1) (Submission.tail l) else Submission.head l;;

(* A bijection from pairs of positive integers to positive integers *)

let cantorPairingFunction x y = ((x + y - 2) * (x + y - 1)) / 2 + y;;

(* Returns true if the value appears in the list, loops forever otherwise *)

let rec find v l = (v = Submission.head l) || find v (Submission.tail l);;

(* integers & head *)

let testHeadOnIntegersReturns1 test_ctxt = assert_equal 1 (Submission.head Submission.integers)

(* integers & tail *)

let testRandomItemInIntegersHasCorrectValue test_ctxt =
    let n = 1 + Random.int 100000 in
    assert_equal n (nth n Submission.integers)
;;

(* map *)

let testMapIntegersToStringsHasCorrectType test_ctxt =
    let checkType (l: string Submission.lazylist) = ()
    and l = Submission.map string_of_int Submission.integers in
    checkType(l)
;;

let testMapIntegersToStringsHasCorrectValues test_ctxt =
    let l = Submission.map string_of_int Submission.integers
    and n = 1 + Random.int 100000 in
    assert_equal (string_of_int n) (nth n l)
;;

(* diag *)

let testDiag test_ctxt =
    let l = Submission.diag (Submission.map (fun n -> Submission.map (fun m -> n * m) Submission.integers) Submission.integers)
    and n = 1 + Random.int 100000 in
    assert_equal (n * n) (nth n l)
;;

(* grid *)

let testGridHasCorrectType test_ctxt =
    let checkType (l: int Submission.lazylist Submission.lazylist) = ()
    and l = Submission.grid Submission.integers Submission.integers (fun x y -> x + y) in
    checkType(l)
;;

let testGridHasCorrectValues test_ctxt =
    let l = Submission.grid Submission.integers Submission.integers cantorPairingFunction
    and n = 1 + Random.int 100000
    and m = 1 + Random.int 100000 in
    assert_equal (cantorPairingFunction n m) (nth m (nth n l))
;;

(* flatten *)

let testFlattenHasCorrectType test_ctxt =
    let checkType (l: int Submission.lazylist) = ()
    and l = Submission.flatten (Submission.grid Submission.integers Submission.integers cantorPairingFunction) in
    checkType(l)
;;

let testFlattenDoesntOmitItems test_ctxt =
    let l = Submission.flatten (Submission.grid Submission.integers Submission.integers cantorPairingFunction)
    and n = 50001 + Random.int 100000 in
    assert_bool "" (find n l)
;;


let suite =
"2015p1q2TestSuite">:::
[
OUnitTest.TestLabel("testHeadOnIntegersReturns1", OUnitTest.TestCase(OUnitTest.Short, testHeadOnIntegersReturns1));

OUnitTest.TestLabel("testRandomItemInIntegersHasCorrectValue", OUnitTest.TestCase(OUnitTest.Short, testRandomItemInIntegersHasCorrectValue));

OUnitTest.TestLabel("testMapIntegersToStringsHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testMapIntegersToStringsHasCorrectType));
OUnitTest.TestLabel("testMapIntegersToStringsHasCorrectValues", OUnitTest.TestCase(OUnitTest.Short, testMapIntegersToStringsHasCorrectValues));

OUnitTest.TestLabel("testDiag", OUnitTest.TestCase(OUnitTest.Short, testDiag));

OUnitTest.TestLabel("testGridHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testGridHasCorrectType));
OUnitTest.TestLabel("testGridHasCorrectValues", OUnitTest.TestCase(OUnitTest.Short, testGridHasCorrectValues));

OUnitTest.TestLabel("testFlattenHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testFlattenHasCorrectType));
OUnitTest.TestLabel("testFlattenDoesntOmitItems", OUnitTest.TestCase(OUnitTest.Short, testFlattenDoesntOmitItems));
];;

let () =
run_test_tt_main suite
;;
