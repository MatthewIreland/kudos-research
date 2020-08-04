open OUnit2;;

Random.self_init ();;

(*helper functions*)

let rec intsFromTo x y = 
if x>y then [] else x::(intsFromTo (x+1) y);;

let intsTo n = 
let rec intsFrom m =
if m>n then [] else m::(intsFrom (m+1))
in
intsFrom 1 ;;

let removeDupes xs = 
	let rec aux y xs = match xs with 
	| [] -> y
	| x::xs -> aux (x::y) (List.filter (fun i-> i!=x) xs) in
aux [] xs;; 

(*intersect*)

let testIntersectionOfEmptyListsIsTheEmptyList= assert_equal [] (Submission.intersect [] []);;

let testIntersectionOfOneEmptyListIsTheEmptyList = 
	let rndList = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int 500))) in
	assert_equal [] (Submission.intersect [] rndList);;

let testIntersectionOnNonEmptyLists = 
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_equal rndList2 (Submission.intersect rndList1 rndList2);;

let testIntersectionResultHasNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_false (findDupes (Submission.intersect rndList1 rndList2));;

(* how to properly override cons? *)
let testIntersectionHasFewerConsThanListElements = 
	let consCount = ref 0 in
	let (~:) x xs = consCount := !consCount +1; (List.cons x xs) in
	(* OCaml does not allow us to define custom operators beginning with ":", 
	so students would need to replace :: with ~: *)
	(* Student's submission would need to be wrapped here *)
	let rec countConsAndElementsInList xs = (!consCount > List.length xs) in
	assert_false countConsAndElementsInList (Submission.intersect rndList1 rndList2);;

let testIntersectionSubmissionOrderIsSameOrderAsFirstList = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_true (checkOrder (Submission.intersect rndList1 rndList 2) rndList1);;

(*subtract*)

let testSubtractionOfEmptyListsIsTheEmptyList= assert_equal [] (Submission.subtract [] []);;

let testSubtractionOfOneEmptyListIsTheEmptyList = 
	let rndList = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int 500))) in
	assert_equal rndList (Submission.subtract [] rndList);;

let testSubtractionOnNonEmptyLists = 
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_equal (intsFromTo rndNum2 rndNum1)(Submission.subtract rndList1 rndList2);;

let testSubtractionResultHasNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_false (findDupes (Submission.subtract rndList1 rndList2));;

(* how to properly override cons? *)
let testSubtractionHasFewerConsThanListElements = 
	let consCount = ref 0 in
	let (~:) x xs = consCount := !consCount +1; (List.cons x xs) in
	(* OCaml does not allow us to define custom operators beginning with ":", 
	so students would need to replace :: with ~: *)
	(* Student's submission would need to be wrapped here *)
	let rec countConsAndElementsInList xs = (!consCount > List.length xs) in
	assert_false countConsAndElementsInList (Submission.subtract rndList1 rndList2);;

let testSubtractionSubmissionOrderIsSameOrderAsFirstList = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_true (checkOrder (Submission.subtract rndList1 rndList 2) rndList1);;


(*union*)

let testUnionOfEmptyListsIsTheEmptyList= assert_equal [] (Submission.union [] []);;

let testUnionOfOneEmptyListIsTheEmptyList = 
	let rndList = intsTo (Random.int 10000) in
	assert_equal rndList (Submission.union [] rndList);;

let testUnionOnNonEmptyLists = 
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_equal rndList1 (Submission.subtract rndList1 rndList2);;

let testUnionResultHasNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_false (findDupes (Submission.union rndList1 rndList2));;

let testUnionSubmissionOrderHasFirstListElementsFirst = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndList1 = (intsTo 500) in
	let rndList2 = (intsTo 300) in
	assert_true (checkOrder (Submission.subtract rndList1 rndList 2) rndList1);;

let suite =
"Focs2TestSuite">:::
[
OUnitTest.TestLabel("testIntersectionOfEmptyListsIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testIntersectionOfEmptyListsIsTheEmptyList));
OUnitTest.TestLabel("testIntersectionOfOneEmptyListIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testIntersectionOfOneEmptyListIsTheEmptyList));
OUnitTest.TestLabel("testIntersectionResultHasNoRepeats", OUnitTest.TestCase(OUnitTest.Short, testIntersectionResultHasNoRepeats));
OUnitTest.TestLabel("testIntersectionHasFewerConsThanListElements", OUnitTest.TestCase(OUnitTest.Short, testIntersectionHasFewerConsThanListElements));
OUnitTest.TestLabel("testIntersectionSubmissionOrderIsSameOrderAsFirstList", OUnitTest.TestCase(OUnitTest.Short, testIntersectionSubmissionOrderIsSameOrderAsFirstList));

OUnitTest.TestLabel("testSubtractionOfEmptyListsIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testSubtractionOfEmptyListsIsTheEmptyList));
OUnitTest.TestLabel("testSubtractionOfOneEmptyListIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testSubtractionOfOneEmptyListIsTheEmptyList));
OUnitTest.TestLabel("testSubtractionResultHasNoRepeats", OUnitTest.TestCase(OUnitTest.Short, testSubtractionResultHasNoRepeats));
OUnitTest.TestLabel("testSubtractionHasFewerConsThanListElements", OUnitTest.TestCase(OUnitTest.Short, testSubtractionHasFewerConsThanListElements));
OUnitTest.TestLabel("testSubtractionSubmissionOrderIsSameOrderAsFirstList", OUnitTest.TestCase(OUnitTest.Short, testSubtractionSubmissionOrderIsSameOrderAsFirstList));

OUnitTest.TestLabel("testUnionOfEmptyListsIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testUnionOfEmptyListsIsTheEmptyList));
OUnitTest.TestLabel("testUnionOfOneEmptyListIsTheEmptyList", OUnitTest.TestCase(OUnitTest.Short, testUnionOfOneEmptyListIsTheEmptyList));
OUnitTest.TestLabel("testUnionResultHasNoRepeats", OUnitTest.TestCase(OUnitTest.Short, testUnionResultHasNoRepeats));
OUnitTest.TestLabel("testUnionSubmissionOrderHasFirstListElementsFirst", OUnitTest.TestCase(OUnitTest.Short, testUnionSubmissionOrderHasFirstListElementsFirst));
];;

let () =
run_test_tt_main suite
;;
