(function(){
    'use strict'
    angular
        .module('app')
        .controller('ProductsController',ProductsController);
        ProductsController.$inject=['$http'];
    function ProductsController($http){
        var vm = this;
        vm.products=[];
        vm.getAll=getAll;
        vm.getAffordable=getAffordable;
        vm.deleteProducts=deleteProducts;
        init();

        function init(){
       getAll(); }

        function getAll(){

           var url="/allProd";
            var productsPromise=$http.get(url);
            productsPromise.then(function(response){
                vm.products=response.data;
            });

        }
        function getAffordable(){

            var price;
            price = document.getElementById("affordablePrice").value;
            var url="/affordable/" + price;
            var productsPromise=$http.get(url);
            productsPromise.then(function(response){
                vm.products=response.data;
            });

        }
        function deleteProducts(id){
            var url="/delete/"+id;
            $http.delete(url).then(function(response){
                vm.products = response.data;

            });

        }

        }


})();