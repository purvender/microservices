variable "project" {
  type = string
}

variable "region" {
  type = string
}

variable "zones" {
  type = list(string)
}
variable "master_version" {
  type = string
}

variable "node_version" {
  type = string
}
