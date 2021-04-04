data "google_project" "this" {
}

resource "google_container_cluster" "this" {
  initial_node_count       = 1
  ip_allocation_policy {
    cluster_ipv4_cidr_block  = "172.16.0.0/12"
    services_ipv4_cidr_block = "192.168.0.0/16"
  }
  location                 = var.region
  master_auth {
    client_certificate_config {
      issue_client_certificate = false
    }
    password = ""
    username = ""
  }
  min_master_version       = var.master_version
  name                     = "my-cluster"
  node_locations           = var.zones
  remove_default_node_pool = true
  workload_identity_config {
    identity_namespace = "${data.google_project.this.project_id}.svc.id.goog"
  }
}

resource "google_container_node_pool" "this" {
  cluster    = google_container_cluster.this.name
  location   = var.region
  management {
    auto_upgrade = false
  }
  name       = "my-node-pool"
  node_config {
    workload_metadata_config {
      node_metadata = "GKE_METADATA_SERVER"
    }
  }
  node_count = 1
  upgrade_settings {
    max_surge       = 1
    max_unavailable = 0
  }
  version    = var.node_version
}
