open OUnit2;;

Random.self_init ();;

(*concatenating lazy lists: concat*)

let testConcatGeneratesLazyList = 
	let rec matchLazyList xs = match xs with
	| Nil -> true
	| Cons (_,_) -> true in
	let rec from k = Cons (k, function () -> from (k+1)) in
	let list1 = from 1 in
	let list2 = from 2 in
	assert_true matchLazyList (Submission.concat list1 list2) ;;

let testConcatProperlyAppendsList2ToList1 =
	let rec from k = Cons (k, function () -> from (k+1)) in
	let list1 = from 1 in
	let list2 = from 2 in
	assert_equal (Lazylist.concat list1 list2) (Submission.concat list1 list2);;
	

(*interleaving lazy lists: combine*)

let testCombineGeneratesLazyList = 
	let rec matchLazyList xs = match xs with
	| Nil -> true
	| Cons (_,_) -> true in
	let rec from k = Cons (k, function () -> from (k+1)) in
	let list1 = from 1 in
	let list2 = from 2 in
	assert_true matchLazyList (Submission.combine list1 list2);;

let testCombineProperlyInterleavesList1AndList2 =
	let rec from k = Cons (k, function () -> from (k+1)) in
	let list1 = from 1 in
	let list2 = from 2 in
	assert_equal (Lazylist.combine list1 list2) (Submission.combine list1 list2);;

(*all0s1s*)

let testAll0s1sGeneratesLazyList = 
	let rec matchLazyList xs = match xs with
	| Nil -> true
	| Cons (_,_) -> true in
	assert_true matchLazyList (Submission.all0s1s);;

let testAll0s1sEachElementInLazyListHasOnly0sand1s = 
	let rec hasOnly1sAnd0s xs = match xs with 
	| [] -> true 
	| x::xs -> (x==1 || x=0) || hasOnly1sAnd0s xs in
	let rec checkOnly1sAnd0s xs = match xs with
	| Nil -> true
	| Cons(a, b) -> if (hasOnly1sAnd0s a) then (checkOnly1sAnd0s (b())) else false in
	assert_true checkOnly1sAnd0s (Submission.all0s1s);;
	
let testAll0s1sContainsElementsFromExample = 
	let rec lazyContainsElements lazy xs = match lazy, xs with
	| _ , [] -> true
	| Nil, xs -> false
	| Cons(a, b), x::xs -> if (a==x) then (lazyContainsElements b() xs)
				else (lazyContainsElements b() (x::xs)) in
	assert_true lazyContainsElements (Submission.all0s1s [[]; [0]; [1]; [0; 0]; [0; 1]; [1; 0]; [1; 1]; [0; 0; 0]])
	
(*palindrome*)

let testPalindromeGeneratesLazyList = 
	let rec matchLazyList xs = match xs with
	| Nil -> true
	| Cons (_,_) -> true in
	assert_true matchLazyList (Submission.palindrome);;

let testEachElementInLazyListIsPalindromic = 
	let isPalindrome xs = (xs = List.rev xs) in
	let rec checkPalindromeLazyList xs = match xs with
	| Nil -> true
	| Cons (a, b) -> if (isPalindrome a) then (checkPalindromeLazyList (b())) else false in
	assert_true checkPalindromeLazyList (Submission.palindrome);;

let suite = "LazyListTestSuite">::: [
  OUnitTest.TestLabel("testConcatGeneratesLazyList", OUnitTest.TestCase(OUnitTest.Short, testConcatGeneratesLazyList ));
  OUnitTest.TestLabel("testConcatProperlyAppendsList2ToList1 ", OUnitTest.TestCase(OUnitTest.Short, testConcatProperlyAppendsList2ToList1));
  OUnitTest.TestLabel("testCombineGeneratesLazyList", OUnitTest.TestCase(OUnitTest.Short, testCombineGeneratesLazyList));
  OUnitTest.TestLabel("testCombineProperlyInterleavesList1AndList2", OUnitTest.TestCase(OUnitTest.Short, testCombineProperlyInterleavesList1AndList2));
  OUnitTest.TestLabel("testAll0s1sGeneratesLazyList", OUnitTest.TestCase(OUnitTest.Short, testAll0s1sGeneratesLazyList));
  OUnitTest.TestLabel("testAll0s1sEachElementInLazyListHasOnly0sand1s", OUnitTest.TestCase(OUnitTest.Short, testAll0s1sEachElementInLazyListHasOnly0sand1s));
  OUnitTest.TestLabel("testAll0s1sContainsElementsFromExample ", OUnitTest.TestCase(OUnitTest.Short, testAll0s1sContainsElementsFromExample ));
  OUnitTest.TestLabel("testPalindromeGeneratesLazyList", OUnitTest.TestCase(OUnitTest.Short, testPalindromeGeneratesLazyList));
  OUnitTest.TestLabel("testEachElementInLazyListIsPalindromic", OUnitTest.TestCase(OUnitTest.Short, testEachElementInLazyListIsPalindromic));
];;

let () = run_test_tt_main suite ;;