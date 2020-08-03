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

(* Randomly generates a list of n paths of length k *)

let rec generatePaths k n = if n > 0 then (generatePath k)::(generatePaths k (n - 1)) else [];;

(* Reference implementation *)

type referenceG = ReferenceGraph of int * (int -> int) * (int -> int);;

let referenceMkgraph(root, left, right) = ReferenceGraph(root, left, right);;

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

let referenceMkgraph2(root, left, right) = ReferenceGraph2(root, left, right, ref CountMap.empty);;

let rec referenceLast2 (ReferenceGraph2(v, left, right, counts)) l =
    match l with
    | [] -> (v, getAndIncrementCount v counts)
    | x::xs -> if x
        then referenceLast2 (ReferenceGraph2(left v, left, right, counts)) xs
        else referenceLast2 (ReferenceGraph2(right v, left, right, counts)) xs
;;

(* mkgraph *)

let testMkgraphHasCorrectType test_ctxt =
    let checkType (graph: Submission.g) = ()
    and left n = n
    and right n = n
    in checkType(Submission.mkgraph(0, left, right))
;;

let testMkgraphOnFiniteGraph test_ctxt =
    (*
        0
       / \
      1   2
     / \ / \
    3   4   5
    *)
    let left n =
        match n with
        | 0 -> 1
        | 1 -> 3
        | 2 -> 4
        | _ -> failwith("left(" ^ (string_of_int n) ^ ") is not defined")
    and right n =
        match n with
        | 0 -> 2
        | 1 -> 4
        | 2 -> 5
        | _ -> failwith("right(" ^ (string_of_int n) ^ ") is not defined")
    in ignore(Submission.mkgraph(0, left, right))
;;

let testMkgraphOnFiniteCyclicGraph test_ctxt =
    (*
       0 <-> 1 <-> 2 <-> 0
    *)
    let left n =
        match n with
        | 0 -> 2
        | 1 -> 0
        | 2 -> 1
        | _ -> failwith("left(" ^ (string_of_int n) ^ ") is not defined")
    and right n =
        match n with
        | 0 -> 1
        | 1 -> 2
        | 2 -> 0
        | _ -> failwith("right(" ^ (string_of_int n) ^ ") is not defined")
    in ignore(Submission.mkgraph(0, left, right))
;;

let testMkgraphOnInfiniteGraph test_ctxt =
    (*
         0
        / \
        \ /
         1
        / \
        \ /
         2
        / \
        \ /
         3
         .
         .
         .
    *)
    let left n = n + 1
    and right n = n + 1
    in ignore(Submission.mkgraph(0, left, right))
;;

(* last *)

let testLastOnEmptyPathReturnsRootVertex test_ctxt =
    let (root, left, right) = generateGraph 10000 in
    let graph = Submission.mkgraph(root, left, right) in
        assert_equal root (Submission.last graph [])
;;

let testLastOnRandomPathReturnsCorrectVertex test_ctxt =
    let params = generateGraph 10000 in
    let referenceGraph = referenceMkgraph params in
    let submissionGraph = Submission.mkgraph params in
    let path = generatePath 500 in
        assert_equal (referenceLast referenceGraph path) (Submission.last submissionGraph path)
;;

(* mkgraph2 *)

let testMkgraph2HasCorrectType test_ctxt =
    let checkType (graph: Submission.g2) = ()
    and left n = n
    and right n = n
    in checkType(Submission.mkgraph2(0, left, right))
;;

let testMkgraph2OnFiniteGraph test_ctxt =
    (*
        0
       / \
      1   2
     / \ / \
    3   4   5
    *)
    let left n =
        match n with
        | 0 -> 1
        | 1 -> 3
        | 2 -> 4
        | _ -> failwith("left(" ^ (string_of_int n) ^ ") is not defined")
    and right n =
        match n with
        | 0 -> 2
        | 1 -> 4
        | 2 -> 5
        | _ -> failwith("right(" ^ (string_of_int n) ^ ") is not defined")
    in ignore(Submission.mkgraph2(0, left, right))
;;

let testMkgraph2OnFiniteCyclicGraph test_ctxt =
    (*
       0 <-> 1 <-> 2 <-> 0
    *)
    let left n =
        match n with
        | 0 -> 2
        | 1 -> 0
        | 2 -> 1
        | _ -> failwith("left(" ^ (string_of_int n) ^ ") is not defined")
    and right n =
        match n with
        | 0 -> 1
        | 1 -> 2
        | 2 -> 0
        | _ -> failwith("right(" ^ (string_of_int n) ^ ") is not defined")
    in ignore(Submission.mkgraph2(0, left, right))
;;

let testMkgraph2OnInfiniteGraph test_ctxt =
    (*
         0
        / \
        \ /
         1
        / \
        \ /
         2
        / \
        \ /
         3
         .
         .
         .
    *)
    let left n = n + 1
    and right n = n + 1
    in ignore(Submission.mkgraph2(0, left, right))
;;

(* last2 *)

let testLast2OnEmptyPathReturnsRootVertex test_ctxt =
    let (root, left, right) = generateGraph 10000 in
    let graph = Submission.mkgraph2(root, left, right) in
    let (vertex, _) = Submission.last2 graph [] in
        assert_equal root vertex
;;

let testLast2OnRandomPathReturnsCorrectVertex test_ctxt =
    let params = generateGraph 10000 in
    let referenceGraph = referenceMkgraph2 params in
    let submissionGraph = Submission.mkgraph2 params in
    let path = generatePath 500 in
    let (referenceVertex, _) = referenceLast2 referenceGraph path in
    let (vertex, _) = Submission.last2 submissionGraph path in
        assert_equal referenceVertex vertex
;;

let testLast2OnRandomPathsReturnsCorrectCounts test_ctxt =
    let params = generateGraph 10000 in
    let referenceGraph = referenceMkgraph2 params in
    let submissionGraph = Submission.mkgraph2 params in
    let paths = generatePaths 500 5 in
    let r = List.map (referenceLast2 referenceGraph) (paths @ List.rev paths) in
    let s = List.map (Submission.last2 submissionGraph) (paths @ List.rev paths) in
        assert_equal r s
;;


let suite =
"1997p1q6TestSuite">:::
[
OUnitTest.TestLabel("testMkgraphHasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testMkgraphHasCorrectType));
OUnitTest.TestLabel("testMkgraphOnFiniteGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraphOnFiniteGraph));
OUnitTest.TestLabel("testMkgraphOnFiniteCyclicGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraphOnFiniteCyclicGraph));
OUnitTest.TestLabel("testMkgraphOnInfiniteGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraphOnInfiniteGraph));

OUnitTest.TestLabel("testLastOnEmptyPathReturnsRootVertex", OUnitTest.TestCase(OUnitTest.Short, testLastOnEmptyPathReturnsRootVertex));
OUnitTest.TestLabel("testLastOnRandomPathReturnsCorrectVertex", OUnitTest.TestCase(OUnitTest.Short, testLastOnRandomPathReturnsCorrectVertex));

OUnitTest.TestLabel("testMkgraph2HasCorrectType", OUnitTest.TestCase(OUnitTest.Short, testMkgraph2HasCorrectType));
OUnitTest.TestLabel("testMkgraph2OnFiniteGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraph2OnFiniteGraph));
OUnitTest.TestLabel("testMkgraph2OnFiniteCyclicGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraph2OnFiniteCyclicGraph));
OUnitTest.TestLabel("testMkgraph2OnInfiniteGraph", OUnitTest.TestCase(OUnitTest.Short, testMkgraph2OnInfiniteGraph));

OUnitTest.TestLabel("testLast2OnEmptyPathReturnsRootVertex", OUnitTest.TestCase(OUnitTest.Short, testLast2OnEmptyPathReturnsRootVertex));
OUnitTest.TestLabel("testLast2OnRandomPathReturnsCorrectVertex", OUnitTest.TestCase(OUnitTest.Short, testLast2OnRandomPathReturnsCorrectVertex));
OUnitTest.TestLabel("testLast2OnRandomPathsReturnsCorrectCounts", OUnitTest.TestCase(OUnitTest.Short, testLast2OnRandomPathsReturnsCorrectCounts));
];;

let () =
run_test_tt_main suite
;;
