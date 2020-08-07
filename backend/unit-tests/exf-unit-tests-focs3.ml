open OUnit2;;

Random.self_init ();;

(*helper functions*)

let rec isIn x lst = match lst with
	| [] -> false
	| hd::tl -> (hd==x) || (isIn x tl);;

let insAllPositions x l = 
	let rec aux prev acc = function
	| [] -> (prev @ [x]) :: acc |> List.rev
	| hd::tl as l -> aux (prev @ [hd]) ((prev @ [x] @ [l]) :: acc) tl in aux [] [] l;;

let rec perms = function
	| [] -> []
	| x::[] -> [[x]]
	| x::xs -> List.fold_left (fun acc p -> acc @ ins_all_positions x p) [] (perms xs);;

(*exf*)

let testExfOnEmptyList = assert_equal [] (Submission.exf (fun x-> x+1) []);;

let testExfOnGivenExampleInQuestion = assert_equal ([2;8] || [8;2]) (Submission.exf (fun x-> x+1) [9;3;2;2;8]);;

let testExfOnLargeExample = 
	let largeExampleList= intsTo 500 in
	assert_equal (isIn perms (intsTo 498)) (Submission.exf (fun x-> x+2) largeExampleList;;

let suite =
"exfTestSuite">:::
[
OUnitTest.TestLabel("testExfOnEmptyList", OUnitTest.TestCase(OUnitTest.Short, testExfOnEmptyList ));
OUnitTest.TestLabel("testExfOnGivenExampleInQuestion", OUnitTest.TestCase(OUnitTest.Short, testExfOnGivenExampleInQuestion));
OUnitTest.TestLabel("testExfOnLargeExample", OUnitTest.TestCase(OUnitTest.Short, testExfOnLargeExample));  
];;

let () = 
run_test_tt_main suite
;;