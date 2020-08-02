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

let rec rndPerm list =
    let rec extract acc n = function
      | [] -> []
      | x ::xs -> if n = 0 then (x, acc @ xs) else extract (x::acc) (n-1) xs
    in
    let extract_rand list len =
      extract [] (Random.int len) list
    in
    let rec aux acc list len =
      if len = 0 then acc else
        let picked, rest = extract_rand list len in
        aux (picked :: acc) rest (len-1)
    in
    aux [] list (List.length list);;

(*intersect*)

let testOnEmptyLists = assert_equal [] (Submission.intersect [] []);;

let testOnOneEmptyList = 
	let rndList = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int 10000))) @ (intsTo (Random.int 10000)) in
	assert_equal [] (Submission.intersect [] rndList);;

let testOnNonEmptyLists = 
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal rndList2 (Submission.intersect rndList1 rndList2);;

let testNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal false (findDupes (Submission.intersect rndList1 rndList2));;

let testSubmissionOrder = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal true (checkOrder (Submission.intersect rndList1 rndList 2) rndList1);;

(*subtract*)

let testOnEmptyLists = assert_equal [] (Submission.subtract [] []);;

let testOnOneEmptyList = 
	let rndList = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int 10000))) @ (intsTo (Random.int 10000)) in
	assert_equal rndList (Submission.subtract [] rndList);;

let testOnNonEmptyLists = 
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal (intsFromTo rndNum2 rndNum1)(Submission.subtract rndList1 rndList2);;

let testNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal false (findDupes (Submission.subtract rndList1 rndList2));;

let testSubmissionOrder = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal true (checkOrder (Submission.subtract rndList1 rndList 2) rndList1);;


(*union*)

let testOnEmptyLists = assert_equal [] (Submission.union [] []);;

let testOnOneEmptyList = 
	let rndList = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int 10000))) @ (intsTo (Random.int 10000)) in
	assert_equal rndList (Submission.union [] rndList);;

let testOnNonEmptyLists = 
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal rndList1 (Submission.subtract rndList1 rndList2);;

let testNoRepeats = 
	let rec findDupes = function
	|[] -> false
	|x::xs -> List. exists ((=)x) xs || findDupes xs in
	assert_equal false (findDupes (Submission.union rndList (10001::rndList)));;

let testSubmissionOrder = 
	let rec checkOrder xs ys = match xs, ys with
	|[], [] -> true
	|[], ys -> true
	|xs, [] -> false
	|x::xs, y::ys -> if (x==y) then checkOrder xs ys
			 else checkOrder (x::xs) ys in
	let rndNum1 = (Random.int 10000) + 1 in
	let rndNum2 = (Random.int rndNum1) + 1 in
	let rndList1 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum1 ))) @ (intsTo (Random.int rndNum1 )) in
	let rndList2 = (List.sort (fun _ _ -> ((Random.int 2) - 1)) (intsTo (Random.int rndNum2 ))) @ (intsTo (Random.int rndNum2 )) in
	assert_equal true (checkOrder (Submission.subtract rndList1 rndList 2) rndList1);;

(*exf*)

let testOnEmptyList = assert_equal [] (Submission.exf (fun x-> x+1) []);;

let testOnExample = assert_equal ([2;8] || [8;2]) (Submission.exf (fun x-> x+1) [9;3;2;2;8]);;