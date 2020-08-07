open OUnit2;;

Random.self_init ();;

(* Generates a random integer between n and 2n - 1 *)

let genRandomItem ?n:(n0=8192) () = n0 + Random.int n0;;

(* Call f n times *)

let repeat n f = for i = 1 to n do f i done;;

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
    repeat 5 (fun _ ->
        let n = genRandomItem() in
        assert_equal n (nth n Submission.integers))
;;

(* map *)

let testMapIntegersToStringsHasCorrectType test_ctxt =
    let checkType (l: string Submission.lazylist) = ()
    and l = Submission.map string_of_int Submission.integers in
    checkType(l)
;;

let testMapIntegersToStringsHasCorrectValues test_ctxt =
    let l = Submission.map string_of_int Submission.integers in
    repeat 5 (fun _ ->
        let n = genRandomItem() in
        assert_equal (string_of_int n) (nth n l))
;;

(* diag *)

let testDiagHasCorrectType test_ctxt =
    let checkType (l: string Submission.lazylist) = ()
    and l = Submission.diag (Submission.map (fun n -> Submission.map (fun m -> string_of_int (n * m)) Submission.integers) Submission.integers) in
    checkType(l)
;;

let testDiagHasCorrectValues test_ctxt =
    let l = Submission.diag (Submission.map (fun n -> Submission.map (fun m -> n * m) Submission.integers) Submission.integers) in
    repeat 5 (fun _ ->
        let n = genRandomItem() in
        assert_equal (n * n) (nth n l))
;;

(* grid *)

let testGridHasCorrectType test_ctxt =
    let checkType (l: string Submission.lazylist Submission.lazylist) = ()
    and l = Submission.grid Submission.integers Submission.integers (fun x y -> string_of_int (x + y)) in
    checkType(l)
;;

let testGridHasCorrectValues test_ctxt =
    let l = Submission.grid Submission.integers Submission.integers cantorPairingFunction in
    repeat 5 (fun _ ->
        let n = genRandomItem()
        and m = genRandomItem() in
        assert_equal (cantorPairingFunction n m) (nth m (nth n l)))
;;

(* flatten *)

let testFlattenHasCorrectType test_ctxt =
    let checkType (l: string Submission.lazylist) = ()
    and l = Submission.flatten (Submission.grid Submission.integers Submission.integers (fun x y -> string_of_int (x + y))) in
    checkType(l)
;;

let testFlattenDoesntOmitItems test_ctxt =
    let l = Submission.flatten (Submission.grid Submission.integers Submission.integers cantorPairingFunction)
    and n = genRandomItem() in
    assert_bool "" (find n l)
;;


let suite =
"2015p1q2TestSuite">:::
[
OUnitTest.TestLabel("testHeadOnIntegersReturns1", OUnitTest.TestCase(OUnitTest.Short, testHeadOnIntegersReturns1));

OUnitTest.TestLabel("testRandomItemInIntegersHasCorrectValue", OUnitTest.TestCase(OUnitTest.Short, testRandomItemInIntegersHasCorrectValue));

OUnitTest.TestLabel("testMapIntegersToStringsHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testMapIntegersToStringsHasCorrectType));
OUnitTest.TestLabel("testMapIntegersToStringsHasCorrectValues", OUnitTest.TestCase(OUnitTest.Short, testMapIntegersToStringsHasCorrectValues));

OUnitTest.TestLabel("testDiagHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testDiagHasCorrectType));
OUnitTest.TestLabel("testDiagHasCorrectValues", OUnitTest.TestCase(OUnitTest.Short, testDiagHasCorrectValues));

OUnitTest.TestLabel("testGridHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testGridHasCorrectType));
OUnitTest.TestLabel("testGridHasCorrectValues", OUnitTest.TestCase(OUnitTest.Short, testGridHasCorrectValues));

OUnitTest.TestLabel("testFlattenHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testFlattenHasCorrectType));
OUnitTest.TestLabel("testFlattenDoesntOmitItems", OUnitTest.TestCase(OUnitTest.Short, testFlattenDoesntOmitItems));
];;

let () =
run_test_tt_main suite
;;
