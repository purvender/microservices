terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 3.45.0"
    }
  }
  required_version = "~> 0.14.9"
}

provider "google" {
  project = var.project
}

module "halyard" {
  source = "./modules/halyard"
  zone   = var.zones[0]
}
module "cluster" {
  source = "./modules/cluster"
  master_version = var.master_version
  node_version   = var.node_version
  region         = var.region
  zones          = var.zones
}
module "storage" {
  source = "./modules/storage"
}
