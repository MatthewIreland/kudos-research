open OUnit2;;

Random.self_init ();;

(* Randomly generates a pair of functions (left, right) representing edges in the tree *)

let generateLeftRight n =
    let array = Array.make n (0, 0) in
        for i = 0 to n - 1 do
            array.(i) <- (Random.int n, Random.int n)
        done;
        let left k = let (l, _) = array.(k) in l
        and right k = let (_, r) = array.(k) in r
        in (left, right)
;;

(* Randomly generates a triple (root, left, right) representing a graph with n vertices *)

let generateGraph n =
    let root = Random.int n
    and (left, right) = generateLeftRight n
    in (root, left, right)
;;

(* Randomly generates a list of k booleans *)

let rec generatePath k = if k > 0 then (Random.bool())::(generatePath (k - 1)) else [];;

(* Randomly generates a list of n paths of length k*)

let rec generatePaths k n = if n > 0 then (generatePath k)::(generatePaths k (n - 1)) else [];;

(* Reference implementation *)

type referenceG = ReferenceGraph of int * (int -> int) * (int -> int);;

let referenceMkGraph(root, left, right) = ReferenceGraph(root, left, right);;

let rec referenceLast (ReferenceGraph(v, left, right)) l =
    match l with
    | [] -> v
    | x::xs -> if x
        then referenceLast (ReferenceGraph(left v, left, right)) xs
        else referenceLast (ReferenceGraph(right v, left, right)) xs
;;

module CountMap = Map.Make(Int);;

let getAndIncrementCount v counts =
    counts := CountMap.update v (function None -> Some 1 | Some n -> Some (n + 1)) !counts;
    CountMap.find v !counts
;;

type referenceG2 = ReferenceGraph2 of int * (int -> int) * (int -> int) * (int CountMap.t) ref;;

let referenceMkGraph2(root, left, right) = ReferenceGraph2(root, left, right, ref CountMap.empty);;

let rec referenceLast2 (ReferenceGraph2(v, left, right, counts)) l =
    match l with
    | [] -> (v, getAndIncrementCount v counts)
    | x::xs -> if x
        then referenceLast2 (ReferenceGraph2(left v, left, right, counts)) xs
        else referenceLast2 (ReferenceGraph2(right v, left, right, counts)) xs
;;

(* last *)

let testLastOnEmptyPath test_ctxt =
    let (root, left, right) = generateGraph 10000 in
    let graph = Submission.mkgraph(root, left, right) in
        assert_equal root (Submission.last graph [])
;;

let testLastOnRandomPath test_ctxt =
    let params = generateGraph 10000 in
    let referenceGraph = referenceMkGraph params in
    let submissionGraph = Submission.mkgraph params in
    let path = generatePath 500 in
        assert_equal (referenceLast referenceGraph path) (Submission.last submissionGraph path)
;;

(* last2 *)

let testLast2OnEmptyPath test_ctxt =
    let (root, left, right) = generateGraph 10000 in
    let graph = Submission.mkgraph2(root, left, right) in
    let s1 = Submission.last2 graph [] in
    let s2 = Submission.last2 graph [] in
    let s3 = Submission.last2 graph [] in
        assert_equal [(root, 1); (root, 2); (root, 3)] [s1; s2; s3]
;;

let testLast2OnRandomPaths test_ctxt =
    let params = generateGraph 10000 in
    let referenceGraph = referenceMkGraph2 params in
    let submissionGraph = Submission.mkgraph2 params in
    let paths = generatePaths 500 5 in
    let r = List.map (referenceLast2 referenceGraph) (paths @ List.rev paths)
    and s = List.map (Submission.last2 submissionGraph) (paths @ List.rev paths)
    in assert_equal r s
;;


let suite =
"1997p1q6TestSuite">:::
[
OUnitTest.TestLabel("testLastOnEmptyPath", OUnitTest.TestCase(OUnitTest.Short, testLastOnEmptyPath));
OUnitTest.TestLabel("testLastOnRandomPath", OUnitTest.TestCase(OUnitTest.Short, testLastOnRandomPath));
OUnitTest.TestLabel("testLast2OnEmptyPath", OUnitTest.TestCase(OUnitTest.Short, testLast2OnEmptyPath));
OUnitTest.TestLabel("testLast2OnRandomPaths", OUnitTest.TestCase(OUnitTest.Short, testLast2OnRandomPaths));
];;

let () =
run_test_tt_main suite
;;
